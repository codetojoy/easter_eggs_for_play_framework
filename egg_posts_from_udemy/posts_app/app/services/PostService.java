package services;

import models.Post;

import javax.inject.Inject;
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
}

