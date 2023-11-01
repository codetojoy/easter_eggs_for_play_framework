package repository;

import io.ebean.DB;
import models.*;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.*;

public class CustomerReferenceRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public CustomerReferenceRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<List<CustomerReference>> getCustomerReferences() {
        return supplyAsync(() -> DB.find(CustomerReference.class).orderBy("id").findList(), executionContext);
    }
}
