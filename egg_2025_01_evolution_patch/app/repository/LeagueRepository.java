package repository;

import io.ebean.DB;
import models.*;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class LeagueRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public LeagueRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<List<League>> getLeague() {
        return supplyAsync(() -> DB.find(League.class).orderBy("id").findList(), executionContext);
    }
}
