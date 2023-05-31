package controllers;

import utils.Timer;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.AccountRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.CompletionStage;

public class AccountController extends Controller {

    private final AccountRepository accountRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public AccountController(AccountRepository accountRepository,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi) {
        this.accountRepository = accountRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> quicklist(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return accountRepository.getAccounts().thenApplyAsync(accountMap -> {
            return ok(views.html.accounts.render(accountMap));
        }, httpExecutionContext.current());
    }

    @SuppressWarnings("unchecked")
    List<String> getAccountIds(AccountMap accountMap) {
        return new ArrayList(accountMap.getKeys());
    }

    List<Integer> generateAccountIds() {
        List<Integer> results = new ArrayList<>();

        int numAccounts = 100_000;
        int seed = 505150;
        for (var i = 0; i < numAccounts; i++) {
            results.add(seed + i);
        }

        return results;
    }

    public CompletionStage<Result> batch2(Http.Request request) {
        String t1 = " t1: " + Thread.currentThread().getId();
        utils.Timer timer = new Timer();
        List<Integer> accountIds = generateAccountIds();
        return accountRepository.doBatch2(accountIds).thenApplyAsync(result -> {
            String t3 = " t3: " + Thread.currentThread().getId();
            String status = timer.getElapsed("batch complete: " + t1 + result + t3);
            return ok(views.html.batch.render(status));
        }, httpExecutionContext.current());
    }

    public Result batch(Http.Request request) {
        utils.Timer timer = new utils.Timer();
        var result = accountRepository.doBatch(generateAccountIds());
        String status = timer.getElapsed("batch complete: " + result);
        return ok(views.html.batch.render(status));
    }

    // this is bonkers, intentionally:
    public CompletionStage<Result> slowlist(Http.Request request) {
        return accountRepository.getAccounts().thenApplyAsync(tmpAccountMap -> {
            Map<String, Account> map = new HashMap<>();

            for (String accountId : getAccountIds(tmpAccountMap)) {
                accountRepository.getAccountByAccountId(accountId).thenApply(account -> {
                    map.put(accountId, account);
                    return account;
                });
            }

            AccountMap accountMap = new AccountMap(map); 

            return ok(views.html.accounts.render(accountMap));
        }, httpExecutionContext.current());
    }
}
