
### Summary

* a sample project spawned from [this one]
* instead of H2, it uses PostgreSQL
* it should be easy to use as a template for MySQL etc 

### Usage

* `./run.sh`
* tested with Java with these (via SDKMan!) :
    * openjdk 11-ea
    * 17.0.5-oracle
    * 21.0.1-oracle

### changes for V 2.9.0

* from [here](https://www.playframework.com/documentation/2.9.x/Migration29)
* compare to [this project](https://github.com/codetojoy/easter_eggs_for_play_framework/tree/main/egg_rest_postgres) (using Play 2.8.x)
    * especially the files `conf/application.conf`, `build.sbt`, `conf/META-INF/persistence.xml`
* project/plugins.sbt
    * `addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.9.0")`
* project/build.properties
    * `sbt.version=1.9.6`
* build.sbt
    * `scalaVersion := "2.13.12"`
* s/HttpExecutionContext/ClassLoaderExecutionContext/
* s/import javax.persistence/import jakarta.persistence/
* CRUCIAL: see [here](https://github.com/orgs/playframework/discussions/11985#discussioncomment-7379124)

