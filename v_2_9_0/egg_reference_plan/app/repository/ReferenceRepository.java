package repository;

import io.ebean.DB;
import models.*;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.*;

public class ReferenceRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public ReferenceRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<List<Reference>> getReferences() {
        return supplyAsync(() -> DB.find(Reference.class).orderBy("id").findList(), executionContext);
    }
}
