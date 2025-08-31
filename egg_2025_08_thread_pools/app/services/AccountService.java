
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

    private final AccountApiExecutionContext accountApiExecContext;
    private final LongRunningExecutionContext longRunningExecContext;

    @Inject
    public AccountService(AccountApiExecutionContext accountApiExecContext,
                          LongRunningExecutionContext longRunningExecContext) {
        this.accountApiExecContext = accountApiExecContext;
        this.longRunningExecContext = longRunningExecContext;
    }

    private void log(String message) {
        MyLogger.log(message);
    }

    // Play default thread pool
    public List<Account> fetch_v1(List<Integer> accountIds) throws Exception {
        return doFetch(accountIds);
    }

    // JVM ForkJoin pool
    public List<Account> fetch_v2(List <Integer> accountIds) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return doFetch(accountIds);
        }).get();
    }

    // Custom ExecutionContext pool
    public List<Account> fetch_v3(List <Integer> accountIds) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return doFetch(accountIds);
        }, longRunningExecContext).get();
        // }, accountApiExecContext).get();
    }

    // virtual threads
    public List<Account> fetch_v4(List <Integer> accountIds) throws Exception {
        ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

        return CompletableFuture.supplyAsync(() -> {
            return doFetch(accountIds);
        }, virtualExecutor).get();
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
                if (threadName.isEmpty()) {
                    threadName = "virtual-" + Long.toString(Thread.currentThread().threadId());
                } 
                accounts.add(new Account(accountId, name, address, threadName));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return accounts;
    }
}
