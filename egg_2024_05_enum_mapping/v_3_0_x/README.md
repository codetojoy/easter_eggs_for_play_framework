
### Summary

* quick app to read a list of `Roster` (players) from the database
* also `League` to test dual mappings
* testing mapping an enum in Ebean 
* this is 3.0.x

### changes from 2.9.x

* imports: akka -> org.apache.pekko
* plugins: com.typesafe.play -> org.playframework 

### Details

* setup database with [this project](https://github.com/codetojoy/gists/tree/main/pg_enum_may_2024)
* run this app
* browse to http://localhost:9000/roster
* browse to http://localhost:9000/league
* ignore other models/controllers, as they are from the original [Play samples](https://github.com/playframework/play-samples/tree/2.9.x)

### Version Info

```
$ java --version
openjdk 21.0.3 2024-04-16 LTS
OpenJDK Runtime Environment Temurin-21.0.3+9 (build 21.0.3+9-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.3+9 (build 21.0.3+9-LTS, mixed mode)
$ scala --version
Scala code runner version 3.3.3 -- Copyright 2002-2024, LAMP/EPFL
$ sbt --version
sbt version in this project: 1.9.7
sbt script version: 1.9.6
```

