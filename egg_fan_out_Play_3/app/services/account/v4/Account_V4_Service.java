
package services.account.v4;

import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import javax.inject.Inject;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

import play.libs.ws.*;

import models.Account;
import utils.*;
import services.AccountApiExecutionContext;

public class Account_V4_Service {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final WSClient wc;
    private final AccountApiExecutionContext ec;

    @Inject
    public Account_V4_Service(WSClient wc, AccountApiExecutionContext ec) {
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

    public List<Account> fetchInfoForAccounts_V4(List<Account> accounts) throws Exception {
        final List<CompletableFuture<Collection<Optional<Account>>>> futures =
            accounts.stream().map(acc -> buildApiCall(acc)).toList();

        CompletableFuture<Collection<Optional<Account>>> aggregateFuture = futures.stream()
                                                                            .reduce(combineApiCalls())
                                                                            .orElse(CompletableFuture.completedFuture(emptyList()));

        Collection<Optional<Account>> receivedAccountsCollection = aggregateFuture.get();
        List<Account> receivedAccounts = receivedAccountsCollection.stream().filter(optionalAcc -> optionalAcc.isPresent()).map(optionalAcc -> optionalAcc.get()).toList();
        return receivedAccounts;
    }

    protected CompletableFuture<Collection<Optional<Account>>> buildApiCall(Account account) {
        String targetURL = buildURL(account);
        utils.Timer timer = new utils.Timer();

        return wc.url(targetURL).get().thenApplyAsync(response -> processApiResponse(response, timer), ec).toCompletableFuture();
    }

    protected Collection<Optional<Account>> processApiResponse(WSResponse response, utils.Timer timer) {
        Optional<Account> accountResponse = Optional.empty();

        try {
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            Account receivedAccount = objectMapper.readValue(responseBody, Account.class);

            MyLogger.log(logger, "wc received response. acc: " + receivedAccount.toString());

            receivedAccount.setThreadId(Thread.currentThread().threadId());
            receivedAccount.setElapsed(timer.getElapsed(""));

            accountResponse = Optional.of(receivedAccount);
        } catch (Exception ex) {
            MyLogger.log(logger, "wc caught exception ex: " + ex.getMessage());
        }

        Collection<Optional<Account>> accountResponses = List.of(accountResponse);

        return accountResponses;
    }

    // https://theboreddev.com/parallel-api-calls-with-completablefuture/

    protected BinaryOperator<CompletableFuture<Collection<Optional<Account>>>> combineApiCalls() {
        return (cf1, cf2) -> cf1
                .thenCombine(cf2, (accounts1, accounts2) -> {
                    return Stream.concat(accounts1.stream(), accounts2.stream()).toList();
                });
    }
}
