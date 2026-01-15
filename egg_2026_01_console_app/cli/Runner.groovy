#!/usr/bin/env groovy

import com.google.inject.Guice
import com.google.inject.Injector
import cli.CliModule
import services.AccountService
import models.Account

// Check command-line arguments
if (args.length == 0) {
    println "Usage: groovy Runner.groovy <account-id1> <account-id2> ..."
    println "Example: groovy Runner.groovy 100 101 102"
    System.exit(1)
}

try {
    // Parse account IDs from command-line arguments
    List<Integer> accountIds = args.collect { it as Integer }
    println "Fetching accounts for IDs: ${accountIds}"

    // Bootstrap Guice injector with CLI module
    Injector injector = Guice.createInjector(new CliModule())

    // Inject AccountService
    AccountService accountService = injector.getInstance(AccountService.class)

    // Call fetch_v0() with account IDs
    List<Account> accounts = accountService.fetch_v4(accountIds)

    // Print results
    println "\n=== Results ==="
    accounts.each { account ->
        println account.toString()
    }

    println "\nSuccessfully fetched ${accounts.size()} account(s)"

} catch (NumberFormatException e) {
    System.err.println "Error: All arguments must be valid integers"
    System.err.println "Provided: ${args}"
    System.exit(1)
} catch (Exception e) {
    System.err.println "Error occurred: ${e.getMessage()}"
    e.printStackTrace()
    System.exit(1)
}
