package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Provide JPA operations running inside of a thread pool sized to the connection pool
 */
public class JPAPlayerRepository implements PlayerRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPAPlayerRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Player> add(Player player) {
        return supplyAsync(() -> wrap(em -> insert(em, player)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Player>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Player insert(EntityManager em, Player player) {
        em.persist(player);
        return player;
    }

    private Stream<Player> list(EntityManager em) {
        List<Player> players = em.createQuery("select p from Player p", Player.class).getResultList();
        return players.stream();
    }
}
