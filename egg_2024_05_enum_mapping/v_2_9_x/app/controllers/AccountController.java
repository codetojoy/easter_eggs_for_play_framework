package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.AccountRepository;

import javax.inject.Inject;
import jakarta.persistence.PersistenceException;
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

    List<String> getAccountIds(AccountMap accountMap) {
        return new ArrayList<String>(accountMap.getKeys());
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
