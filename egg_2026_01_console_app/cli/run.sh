#!/bin/bash

# Runner script for CLI application
# Usage: ./cli/run.sh <account-id1> <account-id2> ...

# Get the project root directory (parent of cli)
PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"

# Get the full classpath from sbt
echo "Building classpath from sbt..."
FULL_CLASSPATH=$(cd "${PROJECT_ROOT}" && sbt "export runtime:fullClasspath" 2>/dev/null | grep -v "^\[" | tail -1)

if [ -z "$FULL_CLASSPATH" ]; then
    echo "Error: Could not get classpath from sbt"
    exit 1
fi

# Run the Groovy script with proper classpath
cd "${PROJECT_ROOT}"
groovy -cp "${FULL_CLASSPATH}" cli/Runner.groovy "$@"
