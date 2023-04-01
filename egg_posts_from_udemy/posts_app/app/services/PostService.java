package services;

import models.Post;
import models.PostRepository;
import forms.PostForm;

import javax.inject.Inject;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class PostService {

    private final PostRepository postRepository;
    List<Post> posts = new ArrayList<Post>();

    @Inject
    public PostService(PostRepository postRepository) {
        System.out.println("TRACER PostService: ctor");
        this.postRepository = postRepository;
/*
        posts.add(new Post("title a", "ps - content1", 5150));
        posts.add(new Post("title b", "ps - content2", 5151));
        posts.add(new Post("title c", "ps - content3", 5152));
*/
    }

    // this is ugly and sync
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

/*
    public CompletionStage<Result> getPlayers() {
        return playerRepository
                .list()
                .thenApplyAsync(playerStream -> ok(toJson(playerStream.collect(Collectors.toList()))), ec.current());
    }
*/

    public Post findPost(Integer postId) {
        var result = getPosts().stream().filter(p -> p.getId().equals(postId)).findAny();
        return result.orElse(null);
    }

    public void addPost(PostForm form) {
        var targetPost = getPosts().stream().max(Comparator.comparing(p -> p.getId())).get();
        var newId = targetPost.getId() + 1;
        posts.add(new Post(form.getTitle(), form.getContent(), newId));
    }
}

