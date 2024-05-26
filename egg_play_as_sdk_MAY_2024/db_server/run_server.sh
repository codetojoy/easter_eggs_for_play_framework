#!/bin/bash

function check_env_var () {
    if [ -n "$1" ]; then
      : # no-op
    else
      echo "usage: please set env var: $2"
      exit -1
    fi
}

check_env_var "$DB_PASSWORD" "DB_PASSWORD"

docker run -p 127.0.0.1:5432:5432 -e POSTGRES_PASSWORD="$DB_PASSWORD" --name my-pg postgres:latest
