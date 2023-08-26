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

    public CompletionStage<Integer> getPlanCount() {
        return supplyAsync(() -> {
            return DB.find(Plan.class)
                     .where()
                     .findCount();
        }, executionContext);
    }

    public CompletionStage<Plan> updatePlan(Long id, String planName, List<String> payload) {
        return supplyAsync(() -> {
            final Plan originalPlan = DB.find(Plan.class).where().eq("id", id).findOne();
            System.out.println("TRACER repo oP: " + originalPlan);
            System.out.println("TRACER TODO update name: " + planName);
            System.out.println("TRACER TODO update payload: " + payload);
            // plan.setName(planName);
            // plan.setPayload(payload);
            // DB.save(plan);
            return originalPlan;
        }, executionContext);
    }

    public CompletionStage<List<Plan>> getPlans() {
        return supplyAsync(() -> {
            return DB.find(Plan.class)
                     .where()
                     .orderBy("id")
                     .findList();
        }, executionContext);
    }
}
