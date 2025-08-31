
package services;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.*;
import org.slf4j.*;

import models.Account;
import utils.MyLogger;

public class AccountService {

    private final AccountApiExecutionContext accountApiExecContext;
    private final LongRunningExecutionContext longRunningExecContext;

    private static final int MIN_DELAY_IN_MS = 50;
    private static final int MAX_DELAY_IN_MS = 750;
    private final Random random = new Random();

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
        return accountIds.stream()
                         .parallel()
                         .map(id -> doFetch(id))
                         .toList();
    }

    // JVM ForkJoin pool
    public List<Account> fetch_v2(List <Integer> accountIds) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return accountIds.stream()
                             .parallel()
                             .map(id -> doFetch(id))
                             .toList();
        }).get();
    }

    // Custom ExecutionContext pool
    public List<Account> fetch_v3(List <Integer> accountIds) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return accountIds.stream()
                             .parallel()
                             .map(id -> doFetch(id))
                             .toList();
        }, longRunningExecContext).get();
        // }, accountApiExecContext).get();
    }

    // virtual threads
    public List<Account> fetch_v4(List <Integer> accountIds) throws Exception {
        ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

        return CompletableFuture.supplyAsync(() -> {
            return accountIds.stream()
                             .parallel()
                             .map(id -> doFetch(id))
                             .toList();
        }, virtualExecutor).get();
    }

    private Account doFetch(Integer accountId) {
        log("fetching info for account " + accountId);
        try {
            long delayInMillis = (long) getRandomDelayInMillis();
            Thread.sleep(delayInMillis); // simulating API call

            String name = "Mozart " + (5150 + accountId);
            String address = accountId + " Queen Street";
            String elapsed = delayInMillis + " ms";
            return new Account(accountId, name, address, getThreadName(), elapsed);
        } catch (InterruptedException e) {
        }
        return null;
    }

    private String getThreadName() {
        String threadName = Thread.currentThread().getName();
        if (threadName.isEmpty()) {
            threadName = "virtual-" + Thread.currentThread().threadId();
        } 
        return threadName;
    }

     private int getRandomDelayInMillis() {
        return new Random().nextInt(MAX_DELAY_IN_MS - MIN_DELAY_IN_MS + 1) + MIN_DELAY_IN_MS;
    }
}
