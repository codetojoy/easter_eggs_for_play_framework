package services.account.api;

import java.util.*;

import org.slf4j.*;

import java.util.concurrent.*;

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
    private final AccountApiExecutionContext ec;
    
    private boolean isFetchDone = false;
    private final ConcurrentLinkedQueue<Page<Account>> queue = new ConcurrentLinkedQueue<>();
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ConcurrentPageSupplier(int pageSize, WSClient wc, AccountApiExecutionContext ec) {
        this.pageSize = pageSize;
        this.wc = wc;
        this.ec = ec;
       
        this.pageInfo = new PageInfo(0, pageSize);
    }

    public Page<Account> nextPage() throws Exception {
        if (!isFetchDone) {
            addToQueue();
            isFetchDone = true;
        }
        return queue.poll();
    }

    // TODO: on another thread
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
        String targetURL = String.format(Constants.ACCOUNT_URL_FORMAT, pageNum, pageSize, maxSize);
        return targetURL;
    }

    protected CompletableFuture<List<Account>> buildApiCall(PageInfo pageInfo) {
        String targetURL = buildURL(pageInfo);
        utils.Timer timer = new utils.Timer();

        return wc.url(targetURL).get().thenApplyAsync(response -> processApiResponse(response, timer), ec).toCompletableFuture();
    }

    protected List<Account> processApiResponse(WSResponse response, utils.Timer timer) {
        List<Account> results = new ArrayList<>();

        try {
            String responseBody = response.getBody();
            ObjectMapper mapper = new ObjectMapper();

            results = mapper.readValue(responseBody, new TypeReference<List<Account>>() {});
        } catch (Exception ex) {
            MyLogger.log(logger, "wc caught exception ex: " + ex.getMessage());
        }

        return results;
    }
}
