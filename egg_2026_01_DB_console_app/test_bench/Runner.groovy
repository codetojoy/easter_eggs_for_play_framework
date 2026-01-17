#!/usr/bin/env groovy

import com.google.inject.Guice
import com.google.inject.Injector
import io.ebean.Database
import test_bench.TestBenchModule
import services.AccountService
import models.*

try {
    // Bootstrap Guice injector with CLI module
    Injector injector = Guice.createInjector(new TestBenchModule())

    // Initialize database (triggers Ebean setup)
    Database database = injector.getInstance(Database.class)

    // Inject AccountService
    AccountService accountService = injector.getInstance(AccountService.class)

    // Get all accounts
    def accountMap = accountService.getAccountMap()

    // Print results
    println "\n=== Results ==="
    def keys = accountMap.getKeys()
    if (keys.isEmpty()) {
        println "No accounts found"
    } else {
        keys.each { accountId ->
            def account = accountMap.getAccount(accountId)
            println "accountId: ${accountId}, account: ${account}"
        }
    }

    println "\nSuccessfully fetched ${keys.size()} account(s)"

} catch (Exception e) {
    System.err.println "Error occurred: ${e.getMessage()}"
    e.printStackTrace()
    System.exit(1)
}
