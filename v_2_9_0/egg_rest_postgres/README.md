
### Summary

* a sample project spawned from [this one]

### Usage

* `./run.sh`

### changes for V 2.9.x

* from [here](https://www.playframework.com/documentation/2.9.x/Migration29)
* project/plugins.sbt
    * `addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.9.0-RC3")`
* project/build.properties
    * `sbt.version=1.9.6`
* build.sbt
    * `scalaVersion := "2.13.12"`
* s/HttpExecutionContext/ClassLoaderExecutionContext/
* s/import java.persistence/import jakarta.persistence/
* see [here](https://github.com/orgs/playframework/discussions/11985#discussioncomment-7379124)

