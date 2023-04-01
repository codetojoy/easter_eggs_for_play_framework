package controllers;

import utils.MyLog;

import forms.PostForm;
import models.Post;
import services.PostService;

import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.i18n.MessagesApi;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.*;

import org.slf4j.*;

public class PostController extends Controller {

    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final MessagesApi messagesApi;
    private final Form<PostForm> form;

    private final PostService postService;

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public PostController(FormFactory formFactory, HttpExecutionContext ec, PostService postService, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.form = formFactory.form(PostForm.class);
        this.ec = ec;
        this.postService = postService;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> index(final Http.Request request, Integer postId) {
        System.out.println("TRACER PostController index cp 0. postId: " + postId);
        if (postId == null) {
            return postService.getPosts().thenApplyAsync(posts -> ok(views.html.posts.render(posts, form, request, messagesApi.preferred(request))), ec.current());
        } else {
            return postService.findPost(postId).thenApplyAsync(post -> {
               System.out.println("TRACER PostController index cp 1. postId: " + postId);
                List<Post> posts = List.of();
                if (post.isPresent()) {
                    posts = List.of(post.get());
                }
                return ok(views.html.posts.render(posts, form, request, messagesApi.preferred(request)));
            }, ec.current());
        }
    }

    public CompletionStage<Result> createPost(Http.Request request) {
        Form<PostForm> boundForm = form.bindFromRequest(request);
        if (boundForm.hasErrors()) {
            return postService.getPosts().thenApplyAsync(posts -> {
                return badRequest(views.html.posts.render(posts, boundForm, request, messagesApi.preferred(request)));
            }, ec.current());
        } else {
            MyLog.log("controller createPost cp 0");
            PostForm data = boundForm.get();
            return postService.addPost(data).thenApplyAsync(post -> {
                MyLog.log("controller createPost cp 1");
                System.out.println("TRACER added new post with title: " + data.getTitle());
                return redirect(routes.PostController.index(null)).flashing("info", "TRACER Post added!");
            }, ec.current());
        }
    }
}
