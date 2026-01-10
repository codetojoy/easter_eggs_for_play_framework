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
import services.ExampleService;

public class ExampleController extends Controller {
    private final ExampleService exampleService;

    private final static int NUM_ACCOUNTS = 25;

    private final static int MODE_0 = 0;

    @Inject
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    protected List<InputAccount> genInputAccounts() {
        List<InputAccount> inputAccounts = new ArrayList<>();
        for (var i = 1; i <= NUM_ACCOUNTS; i++) {
            inputAccounts.add(new InputAccount(i, "Name" + i, "Address" + i, "N/A", ""));
        }
        return inputAccounts;
    }

    public Result getAccounts_v2026(Http.Request request) throws Exception {
        return internalGetAccounts(MODE_0);
    }

    public Result internalGetAccounts(int mode) throws Exception {
        var inputAccounts = genInputAccounts();

        var timer = new utils.Timer();
        List<Account> receivedAccounts = List.of();

        if (mode == MODE_0) {
            receivedAccounts = exampleService.fetch_v2026(inputAccounts);
        }
        
        String timeMessage = "time: " + timer.getElapsed();

        return ok(views.html.accounts.render(receivedAccounts, timeMessage));
    }
}
