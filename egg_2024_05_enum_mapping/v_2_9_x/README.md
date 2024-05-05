
### Summary

* quick app to read a list of `Roster` (players) from the database
* testing mapping an enum in Ebean 
* this is 2.9.x

### changes from 2.8.x

* javax.persistence
* attempt to use Java 21 didn't work, even with sbt 1.9.9`
* change build.sbt for new Scala versions
* remove "-Werror" from build.sbt
* LATEST: the error cannot be reproduced
    * elsewhere, I get a problem with mapping enums

### Details

* setup database with [this project](https://github.com/codetojoy/gists/tree/main/pg_enum_may_2024)
* run this app
* browse to http://localhost:9000/roster
* ignore other models/controllers, as they are from the original [Play samples](https://github.com/playframework/play-samples/tree/2.9.x)

