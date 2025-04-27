package services.account.api;

import java.util.*;

import org.slf4j.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

import play.libs.ws.*;

import models.Account;
import utils.*;
import services.AccountApiExecutionContext;

public class ConcurrentPageSupplier implements PageSupplier<Account> {
    private PageInfo pageInfo;

    private final int pageSize;
    private final WSClient wc;
    private final AccountApiExecutionContext execContext;
    
    private final AtomicBoolean isFetchDone = new AtomicBoolean(false);
    private final ConcurrentLinkedQueue<Page<Account>> queue = new ConcurrentLinkedQueue<>();
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ConcurrentPageSupplier(int pageSize, WSClient wc, AccountApiExecutionContext execContext) {
        this.pageSize = pageSize;
        this.wc = wc;
        this.execContext = execContext;
       
        this.pageInfo = new PageInfo(0, pageSize);
    }

    public Page<Account> nextPage() throws Exception {
        if (!isFetchDone.get()) {
            CompletableFuture.runAsync(() -> {
                try { 
                    addToQueue();
                } catch (Exception ex) {
                    MyLogger.log(logger, "wc caught exception ex: " + ex.getMessage());
                }
            }, execContext);
        }

        return queue.poll();
    }

    // executes on background thread 
    protected void addToQueue() throws Exception {
        boolean isDone = false;

        while (!isDone) {
            String targetURL = buildURL(pageInfo);
            System.out.println("targetURL: " + targetURL);
            utils.Timer timer = new utils.Timer();

            WSResponse response = wc.url(targetURL).get().toCompletableFuture().get();
            List<Account> accounts = processApiResponse(response, timer);

            MyLogger.log(logger, "wc received response. acc: " + accounts.toString());

            Page<Account> page = new Page<>(accounts);
            queue.add(page);

            if (page.isPoisonPill()) {
                isDone = true;
                isFetchDone.set(true);
                break;
            }

            this.pageInfo = nextPageInfo();
        }
    }

    protected PageInfo nextPageInfo() {
        int nextPageNum = pageInfo.pageNum + 1;
        return new PageInfo(nextPageNum, pageSize);
    }

    protected String buildURL(PageInfo pageInfo) {
        int pageNum = pageInfo.pageNum;
        int pageSize = pageInfo.pageSize;   
        int maxSize = 50;
        String delayInSeconds = "" + (int)(Math.random() * 3);
        String targetURL = String.format(Constants.ACCOUNT_URL_FORMAT, pageNum, pageSize, maxSize);
        return targetURL;
    }

    protected List<Account> processApiResponse(WSResponse response, utils.Timer timer) {
        List<Account> results = new ArrayList<>();

        try {
            String responseBody = response.getBody();
            ObjectMapper mapper = new ObjectMapper();

            results = mapper.readValue(responseBody, new TypeReference<List<Account>>() {});

            for (Account account : results) {
                account.setThreadId(Thread.currentThread().threadId());
                account.setElapsed(timer.getElapsed(""));
            }
        } catch (Exception ex) {
            MyLogger.log(logger, "wc caught exception ex: " + ex.getMessage());
        }

        return results;
    }
}
