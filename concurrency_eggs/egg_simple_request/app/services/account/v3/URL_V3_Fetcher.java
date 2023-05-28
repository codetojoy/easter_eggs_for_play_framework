
package services.account.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

import models.*;
import utils.*;
import services.AccountApiExecutionContext;

import play.libs.ws.*;

class URL_V3_Fetcher {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final AtomicInteger counter = new AtomicInteger(0);

    public CompletableFuture<Collection<Account>> fetch(AccountApiExecutionContext ec, WSClient wc, String url) {
        return CompletableFuture.supplyAsync(() -> {
            int count = counter.incrementAndGet();
            MyLogger.log(logger, "begin fetching URL count: " + count);
            Account result = null;

            try {
                Timer timer = new Timer();

                result = makeRequest(wc, url);

                MyLogger.log(logger, "INFO " + timer.getElapsed("URLFetcher fetch"));
            } catch (Exception ex) {
                MyLogger.log(logger, "ERROR caught ex: " + ex.getMessage());
            } finally {
                count = counter.decrementAndGet();
            }
            MyLogger.log("end fetching URL count: " + count);

            return List.of(result);
        }, ec);
    }

    protected Account makeRequest(WSClient wc, String url) {
        Account result = null;

        try {
            Timer timer = new Timer();
            String responseBody = wc.url(url).get().toCompletableFuture().get().getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(responseBody, Account.class);

            result.setThreadId(Thread.currentThread().getId());
            result.setElapsed(timer.getElapsed(""));
            MyLogger.log(logger, "URLFetcher OK");
        } catch (Exception ex) {
            result = new Account();
            result.setElapsed("INTERNAL ERROR");
            MyLogger.log(logger, "URLFetcher ERROR caught ex: " + ex.getMessage());
        }

        return result;
    }
}
