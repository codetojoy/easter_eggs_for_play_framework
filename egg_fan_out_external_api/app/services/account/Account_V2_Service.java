
package services.account;

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

public class Account_V2_Service {
    private static final String TARGET_URL_FORMAT = Constants.ACCOUNT_URL_FORMAT;
    private ExecutorService executor = Executors.newFixedThreadPool(10);
    private final WSClient wc;

    @Inject
    public Account_V2_Service(WSClient wc) {
        this.wc = wc;
    }

    public void shutdown() {
        executor.shutdown();
    }

    public CompletableFuture<Collection<Account>> fetchInfoForAccounts_V2(List<Account> accounts) throws Exception {
        final List<CompletableFuture<Collection<Account>>> futures =
            accounts.stream().map(account -> {
                int id = account.getId();
                String name = account.getName();
                String address = account.getAddress();
                int delayInSeconds = new MyRandom().getRandomInclusive(1,3);
                String targetURL = String.format(TARGET_URL_FORMAT, id, name, address, delayInSeconds);
                Account accountResponse = new Account();
                try {
                    WSResponse response = wc.url(targetURL).get().toCompletableFuture().get();
                    String responseBody = response.getBody();
                    ObjectMapper objectMapper = new ObjectMapper();
                    accountResponse = objectMapper.readValue(responseBody, Account.class);
                } catch (Exception ex) {
                    accountResponse.setName("INTERNAL ERROR");
                }

                CompletableFuture<Collection<Account>> future = CompletableFuture.completedFuture(List.of(accountResponse));
                return future;
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
