
package services.account;

import org.slf4j.*;

import java.util.*;

import javax.inject.Inject;

import models.Account;
import utils.*;

public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Account> fetchAccounts() throws Exception {
        List<Account> accounts = new ArrayList<>();

        int numAccounts = 50;

        for (int i = 1; i <= numAccounts; i++) {
            accounts.add(buildAccount(i));
        }

        return accounts;
    }

    private Account buildAccount(int id) {
        String name = "name-" + id;
        String address = "address-" + id;

        return new Account(id, name, address);
    }
}
