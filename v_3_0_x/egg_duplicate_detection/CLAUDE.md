# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Play Framework 3.0.0 Java application demonstrating duplicate detection functionality. It uses Scala 2.13.12 for template compilation, Java for application code, and includes custom action timing middleware.

## Build System

The project uses SBT (Scala Build Tool) with convenience shell scripts.

### Common Commands

**Compile:**
```bash
./compile.sh
# or directly:
sbt compile
```

**Run the application:**
```bash
./run.sh
# This cleans all targets and runs: sbt clean compile run
```

**Run tests:**
```bash
./test.sh
# or directly:
sbt test
```

**Clean build artifacts:**
```bash
./clean.sh
# Removes target/, project/target/, and project/project/target/
```

**Run a single test:**
```bash
sbt "testOnly TimerTest"
sbt "testOnly it.IntegrationTest"
```

## Architecture

### Request Timing Middleware

The application implements custom request timing using Play's `ActionCreator` interface:

- `ActionCreator.java` - Wraps all controller actions with timing logic
- `Timer.java` - Utility class for measuring request duration
- Logs requests that exceed the threshold (1 second by default)
- Output format: "TRACER: ActionCreator <request> X.XXX seconds elapsed"

The ActionCreator automatically wraps every request, measuring execution time and logging slow requests to stdout.

### Routing Structure

The application uses two routing configurations:

1. **Main routes** (`conf/routes`) - Handles root endpoints and delegates to sub-routers
2. **Posts sub-router** (`conf/posts.routes`) - Handles REST API endpoints under `/v1/posts`

The main routes file uses the `->` syntax to delegate to the posts router. Note: The integration tests reference `v1.post.*` classes (PostController, PostRepository, PostResource, PostData) but these classes are not present in the current codebase - tests will fail until implemented.

### Controller Pattern

Controllers follow standard Play Java conventions:
- Use `@Inject` constructor injection for dependencies
- Inject `Environment` and `Config` from Typesafe Config
- Return Play `Result` objects from action methods
- Render Scala HTML templates via `views.html.*`

### Views

Views are Twirl templates (`.scala.html`) that accept typed parameters:
- `index.scala.html` - Main page
- `foobar.scala.html` - Secondary page

Both views currently accept `Environment` parameter for accessing deployment mode information.

## Testing

### Test Structure

- **Unit tests** in `test/` - Example: `TimerTest.java`
- **Integration tests** in `test/it/` - Example: `IntegrationTest.java`

Integration tests extend `WithApplication` to bootstrap the full Play application with Guice dependency injection.

### Test Dependencies

The integration tests expect a REST API implementation with:
- `v1.post.PostRepository` - Data access layer
- `v1.post.PostController` - REST controller
- `v1.post.PostResource` - Response DTO
- `v1.post.PostData` - Request DTO

These classes are referenced but not implemented in the current codebase.

## Configuration

### Application Config

`conf/application.conf` disables HTTP filters:
```
play.http.filters = play.api.http.NoHttpFilters
```

### Persistence

JPA/Hibernate configuration in `conf/META-INF/persistence.xml`:
- Persistence unit: `defaultPersistenceUnit`
- Data source: `DefaultDS`
- Auto-update schema: `hibernate.hbm2ddl.auto = update`

### Compiler Settings

The project enforces strict Java compilation:
- `-Xlint:unchecked` - Warn about unchecked operations
- `-Xlint:deprecation` - Warn about deprecated API usage
- `-Werror` - Treat warnings as errors

## Dependencies

Key dependencies from `build.sbt`:
- Play Framework 3.0.0 (Java)
- Dropwizard Metrics 4.2.21 - Application metrics
- Palominolabs URL Builder 1.1.5 - URL construction utilities
- Failsafe 2.4.4 - Failure handling and circuit breaker patterns
- Guice - Dependency injection (via Play)

## Development Notes

### Cross-Compilation

The project supports both Scala 2.13.12 and Scala 3.3.1 for template compilation (though application code is Java).

### Resource Externalization

`PlayKeys.externalizeResources := false` keeps resources bundled in the JAR.

### Running on JDK 25

Recent commits indicate the project has been updated to work with JDK 25 and Play 3.0.9.
