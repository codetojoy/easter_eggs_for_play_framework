
### Summary

This project is an existing Play application that has some basic controllers and services
for querying a database.
It is working fine, but we want to use it as a basis for a new command-line application.

### Goal

The `~/cli` folder contains an older version of a headless Groovy script that allows
us to use services from the command-line. 
We want to update the CLI, headless app so that:

* TODO: the new code should inject (or provide) `AccountService` to `Runner.groovy` and call `getAccountMap()` , filtering a list of account-ids from the command-line. The challenge here is to provide credentials to the database (as configured in `conf/application.conf`).
