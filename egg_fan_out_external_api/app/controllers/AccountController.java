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
import services.account.*;

@Singleton
public class AccountController extends Controller {

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;
    private List<Account> accounts = new ArrayList<>();

    private HttpExecutionContext ec;
    private WSClient ws;

    private final AccountService accountService;

    @Inject
    public AccountController(HttpExecutionContext ec, WSClient ws, AccountService accountService) {
        this.ec = ec;
        this.ws = ws;
        this.accountService = accountService;

        var account1 = new Account();
        account1.setName("name-1");
        account1.setId(5150);
        account1.setEnrolled(false);

        Account account2 = new Account();
        account2.setName("name-2");
        account2.setId(6160);
        account1.setEnrolled(true);

        Account account3 = new Account();
        account3.setName("name-3");
        account3.setId(4140);
        account1.setEnrolled(false);

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result getAccounts(Http.Request request) throws Exception {
        List<Account> accounts = new ArrayList<>();
        int numAccounts = 50;
        for (var i = 1; i <= numAccounts; i++) {
            int id = i * i;
            String name = "acct-" + (5150 + i);
            String address = i + "_Longworth_Ave";
            accounts.add(new Account(id, name, address));
        }

        var timer = new utils.Timer();
        CompletableFuture<Collection<Account>> future = accountService.fetchInfoForAccounts(accounts);
        Collection<Account> receivedAccountsCollection = future.get();
        List<Account> receivedAccounts = new ArrayList<>(receivedAccountsCollection);
        utils.Logger.log(timer.getElapsed("App overall request time"));

        for (Account account : receivedAccounts) {
            utils.Logger.log("App received account: " + account.toString());
        } 

        return ok(views.html.accounts.render(receivedAccounts));
/*
*/
    }
}
