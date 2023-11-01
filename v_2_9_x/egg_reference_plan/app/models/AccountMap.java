package models;

import java.util.*;


public class AccountMap {
    final private Map<String, Account> accountMap;

    public AccountMap(Map<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

    public Set<String> getKeys() {
        return accountMap.keySet();
    }

    public Account getAccount(String accountId) {
        return accountMap.get(accountId);
    } 
}

