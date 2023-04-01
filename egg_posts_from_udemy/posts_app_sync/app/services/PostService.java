package services;

import utils.MyLog;

import models.Post;
import models.PostRepository;
import forms.PostForm;

import javax.inject.Inject;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class PostService {

    private final PostRepository postRepository;

    @Inject
    public PostService(PostRepository postRepository) {
        System.out.println("TRACER PostService: ctor");
        this.postRepository = postRepository;
    }

    // this is ugly and blocking
    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<Post>();

        try { 
            posts = postRepository.list()
                                  .toCompletableFuture()
                                  .get()
                                  .map(p -> (Post) p)
                                  .collect(Collectors.toList());
        } catch(Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
        return posts;
    }

    // this is ugly and blocking 
    public Post findPost(Integer postId) {
        Post post = null;
    
        try {
            post = postRepository.find(postId)
                                 .toCompletableFuture()
                                 .get()
                                 .get();
        } catch(Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }

        return post;
    }

    // this is ugly and blocking 
    public void addPost(PostForm form) {
        // var targetPost = getPosts().stream().max(Comparator.comparing(p -> p.getId())).get();
        // var newId = targetPost.getId() + 1;
        MyLog.log("service addPost cp 0");
        Post post = new Post(form.getTitle(), form.getContent(), null);
        try {
            postRepository.add(post).toCompletableFuture().get();
        } catch(Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}

