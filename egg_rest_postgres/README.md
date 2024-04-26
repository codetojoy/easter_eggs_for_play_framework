
* from play-java-rest-api-example in Play samples

### Usage: database

* see `my_resources/database` for PostgreSQL in Docker
    * see `README.md` in that folder
    * scripts for server, client, PgAdmin
    * Groovy scripts to create/populate/read database tables

### Usage: application

* requires Java, sbt 
* to run: `./run.sh`
* to test: `./test.sh`

### Notes

* from example
* PostAction.java has a timer for a service call that will return 500 with error page
* for my experimentation, I have changed this to 10 seconds

* my Timer
* see `./app/ActionCreator.java` and `./app/Timer.java`
* I have introduced a pathogen/sleep into `JPAPostRepository.java`



