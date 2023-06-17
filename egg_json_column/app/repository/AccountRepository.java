package repository;

import io.ebean.DB;
import models.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class AccountRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public AccountRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<AccountMap> getAccounts() {
        return supplyAsync(() -> DB.find(Account.class).orderBy("account_id").findList(), executionContext)
                .thenApply(list -> {
                    Map<String, Account> accounts = new HashMap<>();
                    for (Account account : list) {
                        accounts.put(account.getAccountId(), account);
                    }
                    return new AccountMap(accounts);
                });
    }

    public CompletionStage<Account> getAccountByAccountId(String accountId) {
        return supplyAsync(() -> DB.find(Account.class).where().eq("accountId", accountId).findOne(), executionContext)
                .thenApply(account -> {
                    return account;
                });
    }
}
