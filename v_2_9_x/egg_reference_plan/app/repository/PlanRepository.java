package repository;

import io.ebean.DB;
import models.*;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.*;

public class PlanRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public PlanRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<List<Plan>> getPlans() {
        return supplyAsync(() -> DB.find(Plan.class).orderBy("id").findList(), executionContext);
    }
}
