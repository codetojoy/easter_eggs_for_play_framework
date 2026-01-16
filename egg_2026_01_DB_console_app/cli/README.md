# CLI Application

A headless command-line interface for the Play webapp that allows you to script services from the command line.

## Overview

We've successfully created a headless command-line interface for your Play webapp. This allows you to use the same services and Guice modules from the command line without running the full web application.

## Files

1. **cli/CliModule.groovy** - Full Play-compatible Guice module that:
   - Provides ActorSystem with your application.conf settings
   - Enables dependency injection for AccountService and both ExecutionContexts
   - Handles proper ActorSystem shutdown

2. **cli/Runner.groovy** - Command-line entry point that:
   - Parses account IDs from arguments
   - Bootstraps Guice with the same modules as the Play webapp
   - Injects AccountService and calls fetch_v4() (or any other fetch method)
   - Displays results

3. **cli/run.sh** - Convenience script that automatically loads the full sbt classpath

## Usage

```bash
./cli/run.sh <account-id1> <account-id2> ...
```

### Example

```bash
./cli/run.sh 5150 6160
```

## How It Works

The solution uses the complete sbt runtime classpath (all Play dependencies) to ensure full compatibility with the Play webapp's Guice modules and services. You can now script your services from the command line!

## Requirements

- Groovy installed and available on PATH
- sbt (used to generate the classpath)
- Compiled Java classes (run `sbt compile` first)
