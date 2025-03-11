
package services.account.api;

import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;

import javax.inject.Inject;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

import play.libs.ws.*;

import models.Account;
import utils.*;
import services.AccountApiExecutionContext;

public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final WSClient wc;
    private final AccountApiExecutionContext ec;

    @Inject
    public AccountService(WSClient wc, AccountApiExecutionContext ec) {
        this.wc = wc;
        this.ec = ec;
    }

    protected String buildURL(Account account) {
        int id = account.getId();
        String name = account.getName();
        String address = account.getAddress();
        int delayInSeconds = new MyRandom().getRandomInclusive(1,5);
        String targetURL = String.format(Constants.ACCOUNT_URL_FORMAT, id, name, address, delayInSeconds);
        return targetURL;
    }

    public List<Account> fetchInfoForAccounts(List<Account> accounts) throws Exception {
        final List<CompletableFuture<Collection<Account>>> futures =
            accounts.stream().map(this::buildApiCall).toList();

        CompletableFuture<Collection<Account>> aggregateFuture = futures.stream()
                                                                        .reduce(combineApiCalls())
                                                                        .orElse(CompletableFuture.completedFuture(List.of()));

        Collection<Account> receivedAccountsCollection = aggregateFuture.get();
        List<Account> receivedAccounts = receivedAccountsCollection.stream().toList();
        return receivedAccounts;
    }

    protected CompletableFuture<Collection<Account>> buildApiCall(Account account) {
        String targetURL = buildURL(account);
        utils.Timer timer = new utils.Timer();

        return wc.url(targetURL).get().thenApplyAsync(response -> processApiResponse(response, timer), ec).toCompletableFuture();
    }

    protected Collection<Account> processApiResponse(WSResponse response, utils.Timer timer) {
        Collection<Account> results = new ArrayList<>();

        try {
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            Account receivedAccount = objectMapper.readValue(responseBody, Account.class);

            MyLogger.log(logger, "wc received response. acc: " + receivedAccount.toString());

            receivedAccount.setThreadId(Thread.currentThread().threadId());
            receivedAccount.setElapsed(timer.getElapsed(""));

            results.add(receivedAccount);
        } catch (Exception ex) {
            MyLogger.log(logger, "wc caught exception ex: " + ex.getMessage());
        }

        return results;
    }

    // https://theboreddev.com/parallel-api-calls-with-completablefuture/

    protected BinaryOperator<CompletableFuture<Collection<Account>>> combineApiCalls() {
        return (cf1, cf2) -> cf1
                .thenCombine(cf2, (accounts1, accounts2) -> {
                    return Stream.concat(accounts1.stream(), accounts2.stream()).toList();
                });
    }
}
