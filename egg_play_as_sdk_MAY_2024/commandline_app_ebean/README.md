
### Info

* derived from [this repo](https://github.com/TuxGamer/ebean-example)
* tested with Java 21, Gradle 8.4

### Prerequisites

* ensure database is running and populated with data

### Usage

* write desired Ebean queries/operations in `app.tasks`
* change `App.java` to use the new task
* edit 'setvars.sh' with db credentials
* at Terminal:
    * `. ./setvars.sh`
    * `./run.sh`
