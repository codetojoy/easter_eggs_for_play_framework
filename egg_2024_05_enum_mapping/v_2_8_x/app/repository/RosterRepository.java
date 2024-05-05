package repository;

import io.ebean.DB;
import models.*;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class RosterRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public RosterRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<List<Roster>> getRoster() {
        return supplyAsync(() -> DB.find(Roster.class).orderBy("id").findList(), executionContext);
    }
}
