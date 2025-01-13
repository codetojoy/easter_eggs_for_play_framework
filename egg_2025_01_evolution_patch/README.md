
### Summary

* a sandbox for experimentation with patching evolutions
* item 1
    * see ~/EvolutionUtility.groovy 
    * this allows amending of a revision
* item 2
    * see EvolutionsController.java
    * this will log evolution info 

### Usage

* start app and visit http://localhost:9000/evolutions to see default evolutions
* stop app
* edit `conf/evolutions/default/3.sql`
* run `groovy EvolutionUtility.groovy`
* apply SQL to database
* start app and visit http://localhost:9000/evolutions to see default evolutions

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

