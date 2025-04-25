
package services.account.api;

import org.slf4j.*;

import java.util.*;
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

/*
 * 
class PageInfo {
    int pageNum;
    int pageSize;
    
    PageInfo(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
 */

public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final WSClient wc;
    private final AccountApiExecutionContext ec;
    private final SimplePageSupplier pageSupplier;

    @Inject
    public AccountService(WSClient wc, AccountApiExecutionContext ec) {
        this.wc = wc;
        this.ec = ec;
        this.pageSupplier = new SimplePageSupplier(10, wc, ec);
    }

    /* 
     * 
    protected String buildURL(PageInfo pageInfo) {
        int pageNum = pageInfo.pageNum;
        int pageSize = pageInfo.pageSize;   
        String targetURL = String.format(Constants.ACCOUNT_URL_FORMAT, pageNum, pageSize);
        return targetURL;
    }
    */

    public List<Account> fetchAccounts() throws Exception {
        List<Account> accounts = new ArrayList<>();
        
        boolean isDone = false;

        while (!isDone) {
            Page<Account> page = pageSupplier.nextPage();

            if (page.isPoisonPill()) {
                isDone = true;
            } else {
                List<Account> records = page.getRecords();
                accounts.addAll(records);
            }
        }

        return accounts;
        /*
         * 
        List<PageInfo> pageInfos = new ArrayList<>();
        pageInfos.add(new PageInfo(0, 10));
        pageInfos.add(new PageInfo(1, 10));

        List<Account> accounts = new ArrayList<>();

        for (var pageInfo : pageInfos) {
            String targetURL = buildURL(pageInfo);
            System.out.println("targetURL: " + targetURL);
            utils.Timer timer = new utils.Timer();

            WSResponse response = wc.url(targetURL).get().toCompletableFuture().get();
            Collection<Account> receivedAccounts = processApiResponse(response, timer);

            MyLogger.log(logger, "wc received response. acc: " + receivedAccounts.toString());

            for (Account account : receivedAccounts) {
                accounts.add(account);
            }
        }

            System.out.println("TRACER num accounts: " + accounts.size());

        return accounts;
         */
    }

    /* 
     * 
    protected CompletableFuture<Collection<Account>> buildApiCall(PageInfo pageInfo) {
        String targetURL = buildURL(pageInfo);
        utils.Timer timer = new utils.Timer();

        return wc.url(targetURL).get().thenApplyAsync(response -> processApiResponse(response, timer), ec).toCompletableFuture();
    }

    protected Collection<Account> processApiResponse(WSResponse response, utils.Timer timer) {
        Collection<Account> results = new ArrayList<>();

        try {
            String responseBody = response.getBody();
            ObjectMapper mapper = new ObjectMapper();

            results = mapper.readValue(responseBody, new TypeReference<List<Account>>() {});
        } catch (Exception ex) {
            MyLogger.log(logger, "wc caught exception ex: " + ex.getMessage());
        }

        return results;
    }
    */
}
