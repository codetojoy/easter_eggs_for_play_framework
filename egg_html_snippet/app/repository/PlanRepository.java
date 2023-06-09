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

    public CompletionStage<Integer> getPlanCount(boolean isFoo) {
        return supplyAsync(() -> {
            // e.g. '[{"isFoo":true}]'
            String rawFormat = "'[{\"%s\": %s}]'";
            String rawValue = String.format(rawFormat, "isFoo", isFoo);

            return DB.find(Plan.class)
                     .where()
                     .raw("payload @> " + rawValue)
                     .findCount();
        }, executionContext);
    }

    public CompletionStage<List<Plan>> getPlans(boolean isFoo) {
        return supplyAsync(() -> {
            // e.g. '[{"isFoo":true}]'
            String rawFormat = "'[{\"%s\": %s}]'";
            String rawValue = String.format(rawFormat, "isFoo", isFoo);

            return DB.find(Plan.class)
                     .where()
                     .raw("payload @> " + rawValue)
                     .orderBy("id")
                     .findList();
        }, executionContext);
    }
}
