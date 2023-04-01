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
        this.postRepository = postRepository;
    }

    public CompletionStage<List<Post>> getPosts() {
        return postRepository.list();
    }

    public CompletionStage<Optional<Post>> findPost(Integer postId) {
        return postRepository.findById(postId);
    }

    public CompletionStage<Post> addPost(PostForm form) {
        MyLog.log("service addPost cp 0");
        Post post = new Post(form.getTitle(), form.getContent(), null);
        return postRepository.add(post);
    }
}

