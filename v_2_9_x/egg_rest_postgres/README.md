
### V 2.9.x

* from [here](https://www.playframework.com/documentation/2.9.x/Migration29)
* project/plugins.sbt
    * `addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.9.0-RC3")`
* project/build.properties
    * `sbt.version=1.9.6`
* build.sbt
    * `scalaVersion := "2.13.12"`
* s/HttpExecutionContext/ClassLoaderExecutionContext/
* s/import java.persistence/import jakarta.persistence/
* changed conf/META-INF/persistence.xml and Hibernate version but it's not happy at runtime


