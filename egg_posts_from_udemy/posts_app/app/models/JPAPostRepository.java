package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Provide JPA operations running inside of a thread pool sized to the connection pool
 */
public class JPAPostRepository implements PostRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPAPostRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Post> add(Post post) {
        return supplyAsync(() -> wrap(em -> insert(em, post)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Post>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage<Optional<Post>> find(int id) {
        return supplyAsync(() -> wrap(em -> find(em, id)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<Post> find(EntityManager em, int id) {
        var query = em.createQuery("select p from Post p where p.id = :id", Post.class);
        query.setParameter("id", id);
        List<Post> posts = query.getResultList();

        Optional<Post> result = Optional.empty();
        if (posts != null && (!posts.isEmpty())) {
            result = Optional.of(posts.get(0));
        } 

        return result;
    }

    private Post insert(EntityManager em, Post post) {
        em.persist(post);
        return post;
    }

    private Stream<Post> list(EntityManager em) {
        List<Post> posts = em.createQuery("select p from Post p", Post.class).getResultList();
        return posts.stream();
    }
}
