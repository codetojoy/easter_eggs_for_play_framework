package repository;

import io.ebean.DB;
import models.*;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.*;

public class ReferencePlanRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public ReferencePlanRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<List<ReferencePlan>> getReferencePlans() {
        return supplyAsync(() -> DB.find(ReferencePlan.class).orderBy("id").findList(), executionContext);
    }
}
