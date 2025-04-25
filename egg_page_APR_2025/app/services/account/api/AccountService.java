
package services.account.api;

import org.slf4j.*;

import java.util.*;

import javax.inject.Inject;

import play.libs.ws.*;

import models.Account;
import utils.*;
import services.AccountApiExecutionContext;

public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final WSClient wc;
    private final AccountApiExecutionContext ec;

    @Inject
    public AccountService(WSClient wc, AccountApiExecutionContext ec) {
        this.wc = wc;
        this.ec = ec;
    }

    public List<Account> fetchAccounts() throws Exception {
        List<Account> accounts = new ArrayList<>();

        var pageSupplier = new SimplePageSupplier(10, wc, ec);
        
        boolean isDone = false;

        while (!isDone) {
            Page<Account> page = pageSupplier.nextPage();

            if (page.isPoisonPill()) {
                isDone = true;
                break;
            } 
            
            List<Account> records = page.getRecords();
            accounts.addAll(records);
        }

        return accounts;
    }
}
