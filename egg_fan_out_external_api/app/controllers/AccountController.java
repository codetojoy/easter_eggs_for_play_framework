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
import services.account.*;

@Singleton
public class AccountController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    private final HttpExecutionContext ec;
    private final WSClient ws;

    private final AccountService accountService;
    private final Account_V2_Service account_V2_Service;

    private final static int NUM_ACCOUNTS = 100;

    @Inject
    public AccountController(HttpExecutionContext ec, WSClient ws, AccountService accountService, Account_V2_Service account_V2_Service) {
        this.ec = ec;
        this.ws = ws;
        this.accountService = accountService;
        this.account_V2_Service = account_V2_Service;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    protected List<Account> genAccounts() {
        List<Account> accounts = new ArrayList<>();
        for (var i = 1; i <= NUM_ACCOUNTS; i++) {
            int id = i * i;
            String name = "acct-" + (5150 + i);
            String address = i + "_Longworth_Ave";
            accounts.add(new Account(id, name, address));
        }
        return accounts;
    }

    public Result getAccounts(Http.Request request) throws Exception {
        var accounts = genAccounts();

        var timer = new utils.Timer();
        CompletableFuture<Collection<Account>> future = accountService.fetchInfoForAccounts(accounts);
        Collection<Account> receivedAccountsCollection = future.get();
        List<Account> receivedAccounts = new ArrayList<>(receivedAccountsCollection);
        String timeMessage = timer.getElapsed("V1 overall request time");
        MyLogger.log(logger, timeMessage);

        for (Account account : receivedAccounts) {
            MyLogger.log(logger, "App received account: " + account.toString());
        } 

        return ok(views.html.accounts.render(receivedAccounts, timeMessage));
    }

    public Result getAccounts_V2(Http.Request request) throws Exception {
        var accounts = genAccounts();

        var timer = new utils.Timer();
        CompletableFuture<Collection<Account>> future = account_V2_Service.fetchInfoForAccounts_V2(accounts);
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
