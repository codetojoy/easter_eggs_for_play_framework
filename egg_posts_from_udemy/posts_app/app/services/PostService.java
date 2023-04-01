package services;

import models.Post;
import forms.PostForm;

import java.util.*;

public class PostService {

    List<Post> posts = new ArrayList<Post>();

    public PostService() {
        System.out.println("TRACER PostService: ctor");
        posts.add(new Post("title a", "ps - content1", 5150));
        posts.add(new Post("title b", "ps - content2", 5151));
        posts.add(new Post("title c", "ps - content3", 5152));
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Post findPost(Integer postId) {
        var result = posts.stream().filter(p -> p.getId().equals(postId)).findAny();
        return result.orElse(null);
    }

    public void addPost(PostForm form) {
        var targetPost = posts.stream().max(Comparator.comparing(p -> p.getId())).get();
        var newId = targetPost.getId() + 1;
        posts.add(new Post(form.getTitle(), form.getContent(), newId));
    }
}

