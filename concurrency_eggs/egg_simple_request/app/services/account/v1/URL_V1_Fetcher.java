
package services.account.v1;

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

class URL_V1_Fetcher {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Account fetch(WSClient wc, String url) {
        MyLogger.log(logger, "begin fetching URL");
        Account result = null;

        try {
            result = makeRequest(wc, url);
        } catch (Exception ex) {
            MyLogger.log(logger, "ERROR caught ex: " + ex.getMessage());
        }
        MyLogger.log("end fetching URL");

        return result;
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
