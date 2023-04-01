package controllers;

import models.Post;
import services.PostService;

import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.*;

public class PostController extends Controller {

    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final PostService postService;

    @Inject
    public PostController(FormFactory formFactory, HttpExecutionContext ec, PostService postService) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.postService = postService;
    }

    public Result index(final Http.Request request, Integer postId) {
        System.out.println("TRACER PostController index cp 0. postId: " + postId);
        if (postId == null) {
            List<Post> posts = postService.getPosts();
            return ok(views.html.posts.render(posts));
        } 
        var post = postService.findPost(postId);
        List<Post> posts = List.of();
        if (post != null) {
            posts = List.of(post);
        }
        return ok(views.html.posts.render(posts));
    }
}

