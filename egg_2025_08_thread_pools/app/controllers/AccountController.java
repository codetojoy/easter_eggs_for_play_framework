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

    private final static int NUM_ACCOUNTS = 25;

    private final static int MODE_0 = 0;
    private final static int MODE_1 = 10;
    private final static int MODE_2a = 21;
    private final static int MODE_2b = 23;
    private final static int MODE_3 = 30;
    private final static int MODE_4 = 40;

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

    public Result getAccounts_v0(Http.Request request) throws Exception {
        return internalGetAccounts(MODE_0);
    }

    public Result getAccounts_v1(Http.Request request) throws Exception {
        return internalGetAccounts(MODE_1);
    }

    public Result getAccounts_v2a(Http.Request request) throws Exception {
        return internalGetAccounts(MODE_2a);
    }

    public Result getAccounts_v2b(Http.Request request) throws Exception {
        return internalGetAccounts(MODE_2b);
    }

    public Result getAccounts_v3(Http.Request request) throws Exception {
        return internalGetAccounts(MODE_3);
    }

    public Result getAccounts_v4(Http.Request request) throws Exception {
        return internalGetAccounts(MODE_4);
    }

    public Result internalGetAccounts(int mode) throws Exception {
        var accountIds = genAccountIds();

        var timer = new utils.Timer();
        List<Account> receivedAccounts = List.of();

        if (mode == MODE_0) {
            receivedAccounts = accountService.fetch_v0(accountIds);
        } else if (mode == MODE_1) {
            receivedAccounts = accountService.fetch_v1(accountIds);
        } else if (mode == MODE_2a) {
            receivedAccounts = accountService.fetch_v2a(accountIds);
        } else if (mode == MODE_2b) {
            receivedAccounts = accountService.fetch_v2b(accountIds);
        } else if (mode == MODE_3) {
            receivedAccounts = accountService.fetch_v3(accountIds);
        } else if (mode == MODE_4) {
            receivedAccounts = accountService.fetch_v4(accountIds);
        }
        
        String timeMessage = timer.getElapsed("time");

        return ok(views.html.accounts.render(receivedAccounts, timeMessage));
    }
}
