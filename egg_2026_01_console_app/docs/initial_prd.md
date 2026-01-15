
### Summary

This project is an existing Play application that has some basic controllers and services.
It is working fine, but we want to use it as a basis for a new command-line application.

### Goal

We want to create a Groovy application that allows us to use services from the Play webapp,
except from the command-line; i.e. a headless version of the webapp. 
The application should have these characteristics:

* TODO: the new code lives in ~/cli
* TODO: the main Groovy file is ~/cli/Runner.groovy
* TODO: For now, we can use @Grape as necessary to express dependencies from `~/build.sbt`
* TODO: the new code should invoke the same Guice modules as the Play webapp for initialization
* TODO: the new code should inject (or provide) `AccountService` to `Runner.groovy` and call `fetch_v0()` with a list of account-ids from the command-line. Something very basic is fine. 
