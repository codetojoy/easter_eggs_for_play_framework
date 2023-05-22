
package services.account;

import java.net.http.*;
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

public class URLFetcher {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public CompletableFuture<Collection<Account>> fetch(Executor executor, String url) {
        return CompletableFuture.supplyAsync(() -> {
            int count = counter.incrementAndGet();
            MyLogger.log("begin fetching URL count: " + count);
            Account result = null;

            try {
                Timer timer = new Timer();

                result = makeRequest(url);

                MyLogger.log("INFO " + timer.getElapsed("URLFetcher fetch"));
            } catch (Exception ex) {
                MyLogger.log("ERROR caught ex: " + ex.getMessage());
            } finally {
                count = counter.decrementAndGet();
            }
            MyLogger.log("end fetching URL count: " + count);

            return List.of(result);
        }, executor);
    }

    protected Account makeRequest(String url) {
        Account result = null;

        try {
            URI targetURI = new URI(url);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                                                 .uri(targetURI)
                                                 .GET()
                                                 .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            Timer timer = new Timer();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(response.body(), Account.class);

            result.setThreadId(Thread.currentThread().getId());
            result.setElapsed(timer.getElapsed(""));
            MyLogger.log("URLFetcher OK");
        } catch (Exception ex) {
            MyLogger.log("URLFetcher ERROR caught ex: " + ex.getMessage());
        }

        return result;
    }
}
