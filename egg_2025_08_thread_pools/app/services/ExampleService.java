
package services;

import javax.inject.Inject;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.*;

import models.*;
import utils.Constants;
import utils.MyLogger;

public class ExampleService {
    private static final int MIN_DELAY_IN_SECONDS = 0;
    private static final int MAX_DELAY_IN_SECONDS = 4;
    
    private final Random random = new Random();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    public ExampleService() {
    }

    private void log(String message) {
        MyLogger.log(message);
    }

    public List<Account> fetch_v2026(List<InputAccount> inputAccounts) throws Exception {
        // goal: use virtual threads to check each inputAccount for inclusion

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<ResultTuple>> futures = inputAccounts.stream()
                .map(inputAccount -> executor.submit(() -> {
                    boolean doInclude = doInclude(inputAccount.getId());
                    return new ResultTuple(inputAccount, doInclude, getThreadName());
                }))
                .toList();

            return futures.stream()
                .map(this::getFutureResult)
                .filter(rt -> rt.doInclude)
                .map(this::toAccount)
                .toList();
        }
    }

    private ResultTuple getFutureResult(Future<ResultTuple> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException("Error getting future result", ex);
        }
    }

    private Account toAccount(ResultTuple resultTuple) {
        InputAccount ia = resultTuple.inputAccount;
        return new Account(
            ia.getId(),
            ia.getName(),
            ia.getAddress(),
            resultTuple.threadName,
            ""
        );
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
        return random.nextInt(MAX_DELAY_IN_SECONDS - MIN_DELAY_IN_SECONDS + 1) + MIN_DELAY_IN_SECONDS;
    }
}

class ResultTuple {
    final InputAccount inputAccount;
    final boolean doInclude;
    final String threadName;

    ResultTuple(InputAccount inputAccount, boolean doInclude, String threadName) {
        this.inputAccount = inputAccount;
        this.doInclude = doInclude;
        this.threadName = threadName;
    }
}