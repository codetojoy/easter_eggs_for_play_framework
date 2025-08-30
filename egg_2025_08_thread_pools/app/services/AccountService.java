
package services;

import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Account;
import utils.MyLogger;
import services.AccountApiExecutionContext;

public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AccountApiExecutionContext execContext;

    @Inject
    public AccountService(AccountApiExecutionContext execContext) {
        this.execContext = execContext;
    }

    private void log(String message) {
        MyLogger.log(message);
    }

    public List<Account> fetch_v1(List<Integer> accountIds) throws Exception {
        return doFetch(accountIds);
    }

    public List<Account> fetch_v2(List <Integer> accountIds) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return doFetch(accountIds);
        }, execContext).get();
    }

    private List<Account> doFetch(List<Integer> accountIds) {
        List<Account> accounts = new ArrayList<>();

        for (Integer accountId : accountIds) {
            log("fetching info for account " + accountId);
            try {
                Thread.sleep(100); // simulating API call
                String name = "Mozart " + (5150 + accountId);
                String address = accountId + " Queen Street";
                String threadName = Thread.currentThread().getName();
                accounts.add(new Account(accountId, name, address, threadName));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return accounts;
    }
}
