package services;

import models.*;
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

import com.typesafe.config.Config;

public class AccountService {
    private final AccountRepository accountRepository;
    private final Config config;

    @Inject
    public AccountService(AccountRepository accountRepository, Config config) {
        this.accountRepository = accountRepository;
        this.config = config;
    }

    public AccountMap getAccountMap() {
        String value = config.getString("sandbox.mode"); // Example usage of config
        System.out.println("TRACER sandbox.mode: " + value);
        return accountRepository.getAccounts();
    }
}
