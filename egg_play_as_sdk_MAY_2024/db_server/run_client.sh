#!/bin/bash

set -e 

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

pgcli postgresql://$DB_USERNAME:$DB_PASSWORD@localhost:5432/postgres


