
### Summary

* quick app to read a list of `Roster` (players) from the database
    * and `League`
* testing mapping an enum in Ebean 
* this is v 2.8.x ; current problem is in 2.9.x
* use Java 11
* LATEST: if Roster.java and League.java use different enum mappings, runtime exception

### Details

* setup database with [this project](https://github.com/codetojoy/gists/tree/main/pg_enum_may_2024)
* run this app
* browse to http://localhost:9000/roster
* ignore other models/controllers, as they are from the original [Play samples](https://github.com/playframework/play-samples/tree/2.8.x)

