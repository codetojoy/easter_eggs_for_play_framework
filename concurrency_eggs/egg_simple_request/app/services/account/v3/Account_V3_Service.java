
package services.account.v3;

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

public class Account_V3_Service {
    private static final String TARGET_URL_FORMAT = Constants.ACCOUNT_URL_FORMAT;
    private final WSClient wc;
    private final AccountApiExecutionContext ec;

    @Inject
    public Account_V3_Service(WSClient wc, AccountApiExecutionContext ec) {
        this.wc = wc;
        this.ec = ec;
    }

    public CompletableFuture<Collection<Account>> fetchInfoForAccounts_V3(List<Account> accounts) {
        final List<CompletableFuture<Collection<Account>>> futures =
            accounts.stream().map(account -> {
                URL_V3_Fetcher fetcher = new URL_V3_Fetcher();
                int id = account.getId();
                String name = account.getName();
                String address = account.getAddress();
                int delayInSeconds = DelayStrategy.DELAY_20_IN_SECONDS;
                String targetURL = String.format(TARGET_URL_FORMAT, id, name, address, delayInSeconds);
                return fetcher.fetch(ec, wc, targetURL);
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
