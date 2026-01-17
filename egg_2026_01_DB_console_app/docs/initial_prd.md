
### Summary

This project is an existing Play application that has some basic controllers and services
for querying a database.

The ultimate goal is a command-line application in `~/test_bench` that allows us to use Java services from Groovy, including querying the database.

### Phase 1 Goal [COMPLETE]

The `~/test_bench` folder contains an older version of a headless Groovy script that allows
us to use services from the command-line. 
We want to update the test_bench, headless app so that:

* TODO 1 [COMPLETE] : the new code should inject (or provide) `AccountService` to `Runner.groovy` and call `getAccountMap()` , filtering a list of account-ids from the command-line. The challenge here is to provide credentials to the database (as configured in `conf/application.conf`).

* TODO 2 [COMPLETE] : refactor `~/test_bench/run.sh` to `~/test_bench/r2.sh` so that the full classpath is cached in a file, and not regenerated from `sbt` every time.

### Phase 2 Goal

* TODO 1. `AccountService` now uses the Play Config and this will need to be initialized so that the test-bench will still work.

