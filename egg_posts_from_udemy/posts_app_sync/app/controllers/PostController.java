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

    public Result index(final Http.Request request, Integer postId) {
        System.out.println("TRACER PostController index cp 0. postId: " + postId);
        if (postId == null) {
            List<Post> posts = postService.getPosts();
            return ok(views.html.posts.render(posts, form, request, messagesApi.preferred(request)));
        }
        var post = postService.findPost(postId);
        List<Post> posts = List.of();
        if (post != null) {
            posts = List.of(post);
        }
        return ok(views.html.posts.render(posts, form, request, messagesApi.preferred(request)));
    }

    public Result createPost(Http.Request request) {
        Form<PostForm> boundForm = form.bindFromRequest(request);

        if (boundForm.hasErrors()) {
            System.err.println("TRACER post errors: " + boundForm.errors());
            return badRequest(views.html.posts.render(postService.getPosts(), boundForm, request, messagesApi.preferred(request)));
            // return redirect(routes.PostController.index(0)).flashing("info", "TRACER Post added!");
            // return badRequest(views.html.index.render(posts, boundForm, request, messagesApi.preferred(request)));
        } else {
            MyLog.log("controller createPost cp 0");
            PostForm data = boundForm.get();
            postService.addPost(data);
            System.out.println("TRACER added new post with title: " + data.getTitle());
            return redirect(routes.PostController.index(null)).flashing("info", "TRACER Post added!");
        }
    }
}

