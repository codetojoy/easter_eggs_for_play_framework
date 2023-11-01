
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

### changes for V 3.0.0

* project/plugins.sbt
    * `addSbtPlugin("org.playframework" % "sbt-plugin" % "3.0.0")`
* s/import akka/import org.apache.pekko/

