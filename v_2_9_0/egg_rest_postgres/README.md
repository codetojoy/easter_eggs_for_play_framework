
### Summary

* a sample project spawned from [this one]
* instead of H2, it uses PostgreSQL
* it should be easy to use as a template for MySQL etc 

### Usage

* `./run.sh`
* I use Java `openjdk 11-ea 2018-09-25` from SDKMan!

### changes for V 2.9.0-RC3

* from [here](https://www.playframework.com/documentation/2.9.x/Migration29)
* compare to [this project](https://github.com/codetojoy/easter_eggs_for_play_framework/tree/main/egg_rest_postgres) (using Play 2.8.x)
    * especially the files `conf/application.conf`, `build.sbt`, `conf/META-INF/persistence.xml`
* project/plugins.sbt
    * `addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.9.0-RC3")`
* project/build.properties
    * `sbt.version=1.9.6`
* build.sbt
    * `scalaVersion := "2.13.12"`
    * use Hibernate 6.x 
* s/HttpExecutionContext/ClassLoaderExecutionContext/
* s/import java.persistence/import jakarta.persistence/
* CRUCIAL: see [here](https://github.com/orgs/playframework/discussions/11985#discussioncomment-7379124)

