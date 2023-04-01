package models;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import java.util.*;

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPAPostRepository.class)
public interface PostRepository {

    CompletionStage<Post> add(Post post);

    CompletionStage<List<Post>> list();

    CompletionStage<Optional<Post>> findById(int id);
}
