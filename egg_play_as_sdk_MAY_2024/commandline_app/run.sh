#!/bin/bash

function check_env_var () {
    if [ -n "$1" ]; then
      : # no-op
    else
      echo "usage: please set env var: $2"
      exit -1
    fi
}

check_env_var "$DB_USERNAME" "DB_USERNAME"
check_env_var "$DB_PASSWORD" "DB_PASSWORD"
check_env_var "$DB_URL" "DB_URL"

stat ../play_webapp/target/universal/play-as-sdk-example-0.9.0.zip > /dev/null 2>&1

if [ $? -eq "0" ]; then
    echo "TRACER found zip"
else 
    echo "TRACER building zip"
    cd ../play_webapp
    sbt clean dist
    cd ..
fi

./gradlew run 
