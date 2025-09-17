
### Summary

* uses Play 3.0.9 
* uses Java 25
* see `AccountService.fetch_v4()` for use of structured concurrency

### Usage

* requires a server such as [this one](https://github.com/codetojoy/simple-server-java)
* `./run.sh` and open http://localhost:9000
* NEW: AccountService no longer sleeps, but calls a simple API 

### TODO

* TBD 

### Version

$ java --version
openjdk 25 2025-09-16
OpenJDK Runtime Environment (build 25+36-3489)
OpenJDK 64-Bit Server VM (build 25+36-3489, mixed mode, sharing)

$ sbt --version
sbt version in this project: 1.11.1
sbt runner version: 1.11.1


