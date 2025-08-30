
package services;

import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    private void log(String message) {
        MyLogger.log(message);
    }

    public List<Account> fetchInfoForAccounts(List<Integer> accountIds) throws Exception {
        List<Account> accounts = new ArrayList<>();

        for (Integer accountId : accountIds) {
            log("fetching info for account " + accountId);
            try {
                Thread.sleep(100); // simulating API call
                String name = "Mozart " + (5150 + accountId);
                String address = accountId + " Queen Street";
                String threadName = Thread.currentThread().getName();
                accounts.add(new Account(accountId, name, address, threadName));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return accounts;
    }
}
