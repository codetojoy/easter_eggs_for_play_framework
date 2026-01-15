
package services;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import play.libs.concurrent.CustomExecutionContext;

import org.slf4j.*;

import models.Account;
import utils.Constants;
import utils.MyLogger;

import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.util.Collection;
import java.util.List;                           
import java.util.Random;                         
import java.util.concurrent.*;                   
import java.util.concurrent.atomic.AtomicInteger;
            
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;

public class AccountService {
    private final ApiExecutionContext apiExecContext;
    private final LongRunningExecutionContext longRunningExecContext;

    private static final int MIN_DELAY_IN_SECONDS = 0;
    private static final int MAX_DELAY_IN_SECONDS = 4;
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
                      // .parallel()
                      .map(id -> CompletableFuture.supplyAsync(() -> doFetch(id), virtualExecutor))
                      .collect(Collectors.toList());

        return getAccounts(futures);
    }

    public List<Account> fetch_v5(List <Integer> accountIds) throws Exception {
        ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

        List<CompletableFuture<Account>> futures = 
            accountIds.stream()
                      .map(id -> CompletableFuture.supplyAsync(() -> doFetch(id), virtualExecutor))
                      .collect(Collectors.toList());

        return getAccounts(futures).stream()
                                   .filter(account -> account.getId() % 2 == 0)
                                   .collect(Collectors.toList());
    }

    private Account doFetch(Integer accountId) {
        log("fetching info for account " + accountId);
        utils.Timer timer = new utils.Timer();
        String name = "Mozart " + (5150 + accountId);
        String address = accountId + " Queen Street";
        int delayInSeconds = getRandomDelayInSeconds();

        String url = String.format(Constants.ACCOUNT_URL_FORMAT, 
                        accountId, 
                        URLEncoder.encode(name, StandardCharsets.UTF_8), 
                        URLEncoder.encode(address, StandardCharsets.UTF_8), 
                        delayInSeconds);
        log("doFetch: url: " + url);
        ApiResult apiResult = doFetch_v2(url);

        String elapsed = timer.getElapsed();
        return new Account(accountId, name, address, getThreadName(), elapsed);
    }

    private ApiResult doFetch_v2(String url) {
        ApiResult result = null;

        try {
            URI targetURI = new URI(url);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                                                 .uri(targetURI)
                                                 .GET()
                                                 .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(response.body(), ApiResult.class);

            // result.setThreadId(Thread.currentThread().threadId());
            // result.setElapsed(timer.getElapsed(""));
            log("url fetcher OK");
        } catch (Exception ex) {
            log("url fetcher: ERROR caught ex: " + ex.getMessage());
        }

        return result;
    }

    private String getThreadName() {
        String threadName = Thread.currentThread().getName();
        if (threadName.isEmpty()) {
            threadName = "virtual-" + Thread.currentThread().threadId();
        } 
        return threadName;
    }

     private int getRandomDelayInSeconds() {
        return new Random().nextInt(MAX_DELAY_IN_SECONDS - MIN_DELAY_IN_SECONDS + 1) + MIN_DELAY_IN_SECONDS;
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
