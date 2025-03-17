
package services.account.api;

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

@Singleton
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final AtomicInteger counter = new AtomicInteger(0);

    private final ReentrantLock lock = new ReentrantLock();

    @Inject
    public AccountService() {
        counter.incrementAndGet();
    }

    private void log(String message) {
        int count = counter.get();
        MyLogger.log(message + "(" + count + ")");
    }

    public List<Account> fetchInfoForAccounts(List<Account> accounts) throws Exception {
        log("pre-lock");
        lock.lock(); // Acquire the lock

        try {
            log("pre-work");
            Thread.sleep(3*1000); // Simulating some work
            log("post-work");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock(); // Release the lock
        }

        return accounts;
    }
}
