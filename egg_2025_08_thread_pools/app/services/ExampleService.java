
package services;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import play.libs.concurrent.CustomExecutionContext;

import org.slf4j.*;

import models.*;
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

public class ExampleService {
    private final ApiExecutionContext apiExecContext;
    private final LongRunningExecutionContext longRunningExecContext;

    private static final int MIN_DELAY_IN_SECONDS = 0;
    private static final int MAX_DELAY_IN_SECONDS = 4;
    private final Random random = new Random();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    public ExampleService(ApiExecutionContext apiExecContext,
                          LongRunningExecutionContext longRunningExecContext) {
        this.apiExecContext = apiExecContext;
        this.longRunningExecContext = longRunningExecContext;
    }

    private void log(String message) {
        MyLogger.log(message);
    }

    public List<Account> fetch_v2026(List<InputAccount> inputAccounts) throws Exception {
        // goal: use virtual threads to check each inputAccount for inclusion

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<ResultTuple>> futures = new ArrayList<>();

            for (InputAccount inputAccount : inputAccounts) {
                Future<ResultTuple> future = executor.submit(() -> {
                    boolean doInclude = doInclude(inputAccount.getId());
                    String threadName = getThreadName();
                    return new ResultTuple(inputAccount, doInclude, threadName);
                });
                futures.add(future);
            }

            List<Account> resultAccounts = new ArrayList<>();
            for (Future<ResultTuple> future : futures) {
                ResultTuple resultTuple = future.get();
                if (resultTuple.doInclude) {
                    InputAccount ia = resultTuple.inputAccount;
                    Account account = new Account(
                        ia.getId(),
                        ia.getName(),
                        ia.getAddress(),
                        resultTuple.threadName,
                        "" // elapsed can be filled in if needed
                    );
                    resultAccounts.add(account);
                }
            }

            return resultAccounts;
        }
    }   

    public boolean doInclude(Integer accountId) {
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

       return accountId % 2 == 0;
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
}

class ResultTuple {
    public final InputAccount inputAccount;
    public final boolean doInclude;
    public final String threadName;

    public ResultTuple(InputAccount inputAccount, boolean doInclude, String threadName) {
        this.inputAccount = inputAccount;
        this.doInclude = doInclude;
        this.threadName = threadName;
    }
}