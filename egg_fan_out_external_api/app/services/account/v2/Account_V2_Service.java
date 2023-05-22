
package services.account.v2;

import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import javax.inject.Inject;

import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

import play.libs.ws.*;

import models.Account;
import utils.*;
import services.AccountApiExecutionContext;

public class Account_V2_Service {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // private ExecutorService executor = Executors.newFixedThreadPool(10);
    private final WSClient wc;
    private final AccountApiExecutionContext ec;

    @Inject
    public Account_V2_Service(WSClient wc, AccountApiExecutionContext ec) {
        this.wc = wc;
        this.ec = ec;
    }

    public void shutdown() {
        // executor.shutdown();
    }

    protected String buildURL(Account account) {
        int id = account.getId();
        String name = account.getName();
        String address = account.getAddress();
        int delayInSeconds = new MyRandom().getRandomInclusive(1,3);
        String targetURL = String.format(Constants.ACCOUNT_URL_FORMAT, id, name, address, delayInSeconds);
        return targetURL;
    }

    public CompletableFuture<Collection<Account>> fetchInfoForAccounts_V2(List<Account> accounts) throws Exception {
        final List<CompletableFuture<Collection<Account>>> futures =
            accounts.stream().map(account -> {
                String targetURL = buildURL(account);
                utils.Timer timer = new utils.Timer();

                return wc.url(targetURL).get().thenApplyAsync(response -> {
                    Account accountResponse = null;

                    try {
                        String responseBody = response.getBody();
                        ObjectMapper objectMapper = new ObjectMapper();
                        accountResponse = objectMapper.readValue(responseBody, Account.class);
                        MyLogger.log(logger, "wc received response. acc: " + accountResponse.toString());
                    } catch (Exception ex) {
                        accountResponse.setName("INTERNAL ERROR");
                        MyLogger.log(logger, "wc caught exception ex: " + ex.getMessage());
                    }

                    accountResponse.setThreadId(Thread.currentThread().getId());
                    accountResponse.setElapsed(timer.getElapsed(""));

                    Collection<Account> accountResponses = List.of(accountResponse);
                    return accountResponses;
                }, ec).toCompletableFuture();
            }).collect(toList());

        return futures.stream()
                .reduce(combineApiCalls())
                .orElse(CompletableFuture.completedFuture(emptyList()));
    }

    // https://theboreddev.com/parallel-api-calls-with-completablefuture/

    protected BinaryOperator<CompletableFuture<Collection<Account>>> combineApiCalls() {
        return (cf1, cf2) -> cf1
                .thenCombine(cf2, (accounts1, accounts2) -> {
                    return Stream.concat(accounts1.stream(), accounts2.stream()).collect(toList());
                });
    }
}
