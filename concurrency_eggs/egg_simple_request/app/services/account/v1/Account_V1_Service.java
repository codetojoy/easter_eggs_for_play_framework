
package services.account.v1;

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

public class Account_V1_Service {
    private static final String TARGET_URL_FORMAT = Constants.ACCOUNT_URL_FORMAT;
    private final WSClient wc;

    @Inject
    public Account_V1_Service(WSClient wc) {
        this.wc = wc;
    }

    public List<Account> fetchInfoForAccounts_V1(List<Account> accounts) {
        URL_V1_Fetcher fetcher = new URL_V1_Fetcher();
        Account account = accounts.get(0);
        int id = account.getId();
        String name = account.getName();
        String address = account.getAddress();
        int delayInSeconds = DelayStrategy.DELAY_20_IN_SECONDS;
        String targetURL = String.format(TARGET_URL_FORMAT, id, name, address, delayInSeconds);
        return List.of(fetcher.fetch(wc, targetURL));
    }
}
