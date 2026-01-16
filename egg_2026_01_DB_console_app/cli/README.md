# CLI Application

A headless command-line interface that queries the database using Play services without running the full web application.

## Overview

This CLI allows you to use `AccountService` from the command line by initializing Ebean ORM directly with database credentials from `conf/application.conf`.

## Files

1. **TestBenchModule.groovy** - Guice module that:
   - Loads database configuration from `conf/application.conf`
   - Initializes Ebean `Database` using `DatabaseFactory`
   - Binds `AccountRepository` and `AccountService` for dependency injection
   - Handles proper database shutdown

2. **Runner.groovy** - Command-line entry point that:
   - Bootstraps Guice with `TestBenchModule`
   - Injects `AccountService` and calls `getAccountMap()`
   - Prints all accounts to the console

3. **run.sh** - Shell script that builds the classpath from sbt and runs the Groovy script

## Usage

```bash
./cli/run.sh
```

## How It Works

The key challenge was initializing Ebean outside of Play Framework. The solution:

1. `TestBenchModule` loads `conf/application.conf` using Typesafe Config
2. Extracts database credentials (`db.default.*` properties)
3. Creates a `DataSourceConfig` and `DatabaseConfig` programmatically
4. Calls `DatabaseFactory.create()` to initialize Ebean as the default server
5. Guice then injects `AccountRepository` into `AccountService`
6. `AccountRepository` uses `io.ebean.DB` which finds the initialized default database

## Requirements

- Groovy installed and available on PATH
- sbt (used to generate the classpath)
- Compiled Java classes (run `sbt compile` first)
- PostgreSQL database running with schema from `conf/evolutions`
