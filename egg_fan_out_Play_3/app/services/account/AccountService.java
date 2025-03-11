
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
import services.AccountApiExecutionContext;

public class AccountService {
    private static final String TARGET_URL_FORMAT = Constants.ACCOUNT_URL_FORMAT;
    // private ExecutorService executor = Executors.newFixedThreadPool(10);
    private final WSClient wc;
    private final AccountApiExecutionContext ec;

    @Inject
    public AccountService(WSClient wc, AccountApiExecutionContext ec) {
        this.wc = wc;
        this.ec = ec;
    }

    public void shutdown() {
        // executor.shutdown();
    }

    public CompletableFuture<Collection<Account>> fetchInfoForAccounts(List<Account> accounts) {
        final List<CompletableFuture<Collection<Account>>> futures =
            accounts.stream().map(account -> {
                URLFetcher fetcher = new URLFetcher();
                int id = account.getId();
                String name = account.getName();
                String address = account.getAddress();
                int delayInSeconds = new MyRandom().getRandomInclusive(1,3);
                // int delayInSeconds = new DelayStrategy().getSimpleMod(id, 10);
                String targetURL = String.format(TARGET_URL_FORMAT, id, name, address, delayInSeconds);
                return fetcher.fetch(ec, targetURL);
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
