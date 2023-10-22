package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.*;
import repository.AccountRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.CompletionStage;

public class AccountController extends Controller {

    private final AccountRepository accountRepository;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public AccountController(AccountRepository accountRepository,
                          ClassLoaderExecutionContext classLoaderExecutionContext,
                          MessagesApi messagesApi) {
        this.accountRepository = accountRepository;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> quicklist(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return accountRepository.getAccounts().thenApplyAsync(accountMap -> {
            return ok(views.html.accounts.render(accountMap));
        }, classLoaderExecutionContext.current());
    }
}
