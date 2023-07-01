package repository;

import io.ebean.DB;
import models.*;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.*;

public class ConfigRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public ConfigRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<Config> getConfig(int id) {
        return supplyAsync(() -> {
            return DB.find(Config.class)
                     .where()
                     .eq("id", id)
                     .findOne();
        }, executionContext);
    }
}
