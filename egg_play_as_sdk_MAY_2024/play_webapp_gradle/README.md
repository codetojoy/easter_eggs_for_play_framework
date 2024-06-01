
### Summary

* test for the Play Gradle plugin (see [here](https://github.com/orgs/playframework/discussions/12338))
    * uses `jakarta.persistence`
    * uses Apache Pekko
* quick app to read some data from the db
* no evolutions, db is setup elsewhere
* TODO: enable many-to-many between Book & Topic

### Details

* set db credentials in app/application.conf 
* `./run.sh`
* browse to http://localhost:9000/books

