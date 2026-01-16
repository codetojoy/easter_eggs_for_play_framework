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
    @Inject
    public AccountRepository() {
    }

    public AccountMap getAccounts() {
        var list = DB.find(Account.class).orderBy("account_id").findList();

        Map<String, Account> accounts = new HashMap<>();
        for (Account account : list) {
            accounts.put(account.getAccountId(), account);
        }

        return new AccountMap(accounts);
    }

    public Account getAccountByAccountId(String accountId) {
        return DB.find(Account.class).where().eq("accountId", accountId).findOne();
    }
}
