package controllers;

import models.*;
import services.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.*;
import repository.AccountRepository;

import javax.inject.Inject;
import jakarta.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.CompletionStage;

public class AccountController extends Controller {

    private final AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Result quicklist(Http.Request request) {
        var accountMap = accountService.getAccountMap();
        return ok(views.html.accounts.render(accountMap));
    }
}
