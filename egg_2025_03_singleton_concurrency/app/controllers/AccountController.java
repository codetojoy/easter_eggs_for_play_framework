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
import services.account.api.AccountService;

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

    protected List<Account> genAccounts() {
        List<Account> accounts = new ArrayList<>();
        for (var i = 1; i <= NUM_ACCOUNTS; i++) {
            int id = i;
            String name = "acct-" + (5150 + i);
            String address = i + "_Longworth_Ave";
            accounts.add(new Account(id, name, address));
        }
        return accounts;
    }

    public Result getAccounts(Http.Request request) throws Exception {
        var accounts = genAccounts();

        var timer = new utils.Timer();
        List<Account> receivedAccounts = accountService.fetchInfoForAccounts(accounts);
        String timeMessage = timer.getElapsed("overall request time");

        return ok(views.html.accounts.render(receivedAccounts, timeMessage));
    }
}
