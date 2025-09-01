
package services;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import play.libs.concurrent.CustomExecutionContext;

import org.slf4j.*;

import models.Account;
import utils.MyLogger;

public class AccountService {
    private final ApiExecutionContext apiExecContext;
    private final LongRunningExecutionContext longRunningExecContext;

    private static final int MIN_DELAY_IN_MS = 100;
    private static final int MAX_DELAY_IN_MS = 2000;
    private final Random random = new Random();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    public AccountService(ApiExecutionContext apiExecContext,
                          LongRunningExecutionContext longRunningExecContext) {
        this.apiExecContext = apiExecContext;
        this.longRunningExecContext = longRunningExecContext;
    }

    private void log(String message) {
        MyLogger.log(message);
    }

    // Play default thread pool
    public List<Account> fetch_v0(List<Integer> accountIds) throws Exception {
        return List.of(doFetch(accountIds.get(0)));
    }

    // Play default thread pool
    // front of house wait staff
    public List<Account> fetch_v1(List<Integer> accountIds) throws Exception {
        return accountIds.stream()
                         .parallel()
                         .map(id -> doFetch(id))
                         .toList();
    }

    // Custom ExecutionContext pool a (api)
    // kitchen staff
    public List<Account> fetch_v2a(List <Integer> accountIds) throws Exception {
        CustomExecutionContext context = apiExecContext;

        List<CompletableFuture<Account>> futures = 
            accountIds.stream()
                      .parallel()
                      .map(id -> CompletableFuture.supplyAsync(() -> doFetch(id), context))
                      .collect(Collectors.toList());
       
        return getAccounts(futures);
    }

    // Custom ExecutionContext pool b (long-running)
    // kitchen staff
    public List<Account> fetch_v2b(List <Integer> accountIds) throws Exception {
        CustomExecutionContext context = longRunningExecContext;

        List<CompletableFuture<Account>> futures = 
            accountIds.stream()
                      .parallel()
                      .map(id -> CompletableFuture.supplyAsync(() -> doFetch(id), context))
                      .collect(Collectors.toList());
       
        return getAccounts(futures);
    }

    // JVM ForkJoin pool
    // utility contractors
    public List<Account> fetch_v3(List<Integer> accountIds) throws Exception {
        List<CompletableFuture<Account>> futures = 
            accountIds.stream()
                      .parallel()
                      .map(id -> CompletableFuture.supplyAsync(() -> doFetch(id)))
                      .collect(Collectors.toList());
       
        return getAccounts(futures);
    }

    // virtual threads
    // special category
    public List<Account> fetch_v4(List <Integer> accountIds) throws Exception {
        ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

        List<CompletableFuture<Account>> futures = 
            accountIds.stream()
                      .parallel()
                      .map(id -> CompletableFuture.supplyAsync(() -> doFetch(id), virtualExecutor))
                      .collect(Collectors.toList());

        return getAccounts(futures);
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

    private List<Account> getAccounts(List<CompletableFuture<Account>> futures) {
        try {
            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                                    .thenApply(v -> futures.stream()
                                                        .map(CompletableFuture::join)
                                                        .collect(Collectors.toList()))
                                    .get();
        } catch (Exception e) {
            return List.of();
        }
    }
}
