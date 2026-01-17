#!/bin/bash

set -e

# Runner script for CLI application
# Usage: ./test_bench/run.sh <account-id1> <account-id2> ...

# Get the project root directory (parent of test_bench)
PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"

stat $PROJECT_ROOT/test_bench/full.classpath.txt
FULL_CLASSPATH=`cat $PROJECT_ROOT/test_bench/full.classpath.txt`

if [ -z "$FULL_CLASSPATH" ]; then
    echo "Error: Could not get classpath from sbt"
    exit 1
fi

# Run the Groovy script with proper classpath
cd "${PROJECT_ROOT}"
groovy -cp "${FULL_CLASSPATH}" test_bench/Runner.groovy "$@"
