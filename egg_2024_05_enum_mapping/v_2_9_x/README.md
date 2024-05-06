
### Summary

* quick app to read a list of `Roster` (players) from the database
* also `League` to test dual mappings
* testing mapping an enum in Ebean 
* this is 2.9.x

### changes from 2.8.x

* javax.persistence
* change build.sbt for new Scala versions
* remove "-Werror" from build.sbt
* LATEST: problem can be reproduced in 2.8.x 
    * elsewhere, I get a problem with mapping enums

### Details

* setup database with [this project](https://github.com/codetojoy/gists/tree/main/pg_enum_may_2024)
* run this app
* browse to http://localhost:9000/roster
* ignore other models/controllers, as they are from the original [Play samples](https://github.com/playframework/play-samples/tree/2.9.x)

