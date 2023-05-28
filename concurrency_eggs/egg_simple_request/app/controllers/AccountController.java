package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.*;

import static play.libs.Scala.asScala;

import play.libs.ws.*;
import play.libs.concurrent.*;

import models.*;
import utils.MyLogger;
import services.account.v1.Account_V1_Service;
import services.account.v3.Account_V3_Service;

@Singleton
public class AccountController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    private final HttpExecutionContext ec;
    private final WSClient ws;

    private final Account_V1_Service account_V1_Service;
    private final Account_V3_Service account_V3_Service;

    private final static int NUM_ACCOUNTS = 1;

    @Inject
    public AccountController(HttpExecutionContext ec, WSClient ws, 
                             Account_V1_Service account_V1_Service,
                             Account_V3_Service account_V3_Service) {
        this.ec = ec;
        this.ws = ws;
        this.account_V1_Service = account_V1_Service;
        this.account_V3_Service = account_V3_Service;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    // v1
    public Result doLongRunningTask_v1(Http.Request request) throws Exception {
        var accounts = Accounts.genAccounts(NUM_ACCOUNTS);

        var timer = new utils.Timer();

        List<Account> receivedAccounts = account_V1_Service.fetchInfoForAccounts_V1(accounts);

        String timeMessage = timer.getElapsed("V2 overall request time");
        MyLogger.log(logger, timeMessage);

        return ok(views.html.accounts.render(receivedAccounts, timeMessage));
    }

    // v3
    public Result doLongRunningTask_v3(Http.Request request) throws Exception {
        var accounts = Accounts.genAccounts(NUM_ACCOUNTS);

        var timer = new utils.Timer();
        CompletableFuture<Collection<Account>> future = account_V3_Service.fetchInfoForAccounts_V3(accounts);
        Collection<Account> receivedAccountsCollection = future.get();
        List<Account> receivedAccounts = new ArrayList<>(receivedAccountsCollection);
        String timeMessage = timer.getElapsed("V2 overall request time");
        MyLogger.log(logger, timeMessage);

        for (Account account : receivedAccounts) {
            MyLogger.log(logger, "App received account: " + account.toString());
        } 

        return ok(views.html.accounts.render(receivedAccounts, timeMessage));
    }
}
