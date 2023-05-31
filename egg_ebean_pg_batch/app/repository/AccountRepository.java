package repository;

import io.ebean.DB;
import io.ebean.Transaction;

import models.*;

import javax.inject.Inject;

import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class AccountRepository {

    private final DatabaseExecutionContext executionContext;
    private final static int BATCH_SIZE = 1000;

    @Inject
    public AccountRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public boolean doBatch(List<Integer> accountIds) {
        boolean result = false;

        try (Transaction transaction = DB.beginTransaction()) {
            // JDBC batch
            transaction.setBatchMode(true);
            transaction.setBatchSize(BATCH_SIZE);

            for (var accountId : accountIds) {
                var account = new Account();
                account.setAccountId(accountId); 
                account.setUsername("user-" + accountId); 
                account.setStatus("batch"); 
                DB.save(account);
            }

            // flush batch and commit
            transaction.commit();
            result = true;
        }

        return result;
    }

    public CompletionStage<String> doBatch2(List<Integer> accountIds) {
        return supplyAsync(() -> {
            String t2 = "" + Thread.currentThread().getId();
            var result = doBatch(accountIds); 
            String s = " t2: " + t2 + " result: " + result; 
            return s;
        }, executionContext); 
    }

    public CompletionStage<AccountMap> getAccounts() {
        return supplyAsync(() -> DB.find(Account.class).orderBy("account_id").findList(), executionContext)
                .thenApply(list -> {
                    Map<String, Account> accounts = new HashMap<>();
                    for (Account account : list) {
                        accounts.put("" + account.getAccountId(), account);
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
