package models;

import utils.MyLog;

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
        MyLog.log("repository add cp abc");
        return supplyAsync(() -> wrap(em -> insert(em, post)), executionContext);
    }

    @Override
    public CompletionStage<List<Post>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage<Optional<Post>> findById(int id) {
        return supplyAsync(() -> wrap(em -> findById(em, id)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<Post> findById(EntityManager em, int id) {
        Optional<Post> result = Optional.empty();

        Post post = em.createQuery("select p from Post p where p.id = :id", Post.class)
                      .setParameter("id", id)
                      .getSingleResult();

        if (post != null) {
            result = Optional.of(post);
        }

        return result;
    }

    private Post insert(EntityManager em, Post post) {
        MyLog.log("repository add cp def... postId: " + post.getId());
        em.persist(post);
        return post;
    }

    private List<Post> list(EntityManager em) {
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }
}
