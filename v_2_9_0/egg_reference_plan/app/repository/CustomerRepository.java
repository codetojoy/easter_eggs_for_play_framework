package repository;

import io.ebean.DB;
import models.*;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.*;

public class CustomerRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public CustomerRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<List<Customer>> getCustomers() {
        return supplyAsync(() -> DB.find(Customer.class).orderBy("id").findList(), executionContext);
    }
}
