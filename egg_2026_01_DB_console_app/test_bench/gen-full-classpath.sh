#!/bin/bash

set -e

PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"

echo "Building classpath from sbt..."
FULL_CLASSPATH=$(cd "${PROJECT_ROOT}" && sbt "export runtime:fullClasspath" 2>/dev/null | grep -v "^\[" | tail -1)

echo $FULL_CLASSPATH > full.classpath.txt
