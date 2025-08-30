package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.*;

import play.libs.ws.*;
import play.libs.concurrent.*;

import models.*;
import utils.MyLogger;
import services.AccountService;

public class AccountController extends Controller {
    private final AccountService accountService;

    private final static int NUM_ACCOUNTS = 10;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    protected List<Integer> genAccountIds() {
        List<Integer> accountIds = new ArrayList<>();
        for (var i = 1; i <= NUM_ACCOUNTS; i++) {
            accountIds.add(i);
        }
        return accountIds;
    }

    public Result getAccounts_v1(Http.Request request) throws Exception {
        var accountIds = genAccountIds();

        var timer = new utils.Timer();
        List<Account> receivedAccounts = accountService.fetch_v1(accountIds);
        String timeMessage = timer.getElapsed("time");

        return ok(views.html.accounts.render(receivedAccounts, timeMessage));
    }

    public Result getAccounts_v2(Http.Request request) throws Exception {
        var accountIds = genAccountIds();

        var timer = new utils.Timer();
        List<Account> receivedAccounts = accountService.fetch_v2(accountIds);
        String timeMessage = timer.getElapsed("time");

        return ok(views.html.accounts.render(receivedAccounts, timeMessage));
    }
}
