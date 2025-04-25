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

@Singleton
public class AccountController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    private final AccountService accountService;

    private final static int NUM_ACCOUNTS = 100;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result getAccounts(Http.Request request) throws Exception {
        var timer = new utils.Timer();
        List<Account> receivedAccounts = accountService.fetchAccounts();
        String timeMessage = timer.getElapsed("overall request time");
        MyLogger.log(logger, timeMessage);

        for (Account account : receivedAccounts) {
            MyLogger.log(logger, "App received account: " + account.toString());
        } 

        return ok(views.html.accounts.render(receivedAccounts, timeMessage));
    }
}
