
### Usage

* requires a mock API server on 8080, such as [this one](https://github.com/codetojoy/simple-server-java)
* run this project and go to http://localhost:9000
* introduces `PageSupplier`
    * `SimplePageSupplier` is synchronous
    * `ConcurrentPageSupplier` writes to a queue
        * TODO: something is wrong when we introduce delay 

### Version

$ java --version
openjdk 21.0.2 2024-01-16 LTS
OpenJDK Runtime Environment Corretto-21.0.2.13.1 (build 21.0.2+13-LTS)
OpenJDK 64-Bit Server VM Corretto-21.0.2.13.1 (build 21.0.2+13-LTS, mixed mode, sharing)

$ sbt --version
sbt version in this project: 1.9.6
sbt script version: 1.9.7


