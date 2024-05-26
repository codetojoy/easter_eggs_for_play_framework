#!/bin/bash

# docker pull dpage/pgadmin4:7.2

function check_env_var () {
    if [ -n "$1" ]; then
      : # no-op
    else
      echo "usage: please set env var: $2"
      exit -1
    fi
}

check_env_var "$DB_EMAIL" "DB_EMAIL"
check_env_var "$DB_PASSWORD" "DB_PASSWORD"

docker run -p 80:80 \
    -e 'PGADMIN_DEFAULT_EMAIL=$DB_EMAIL' \
    -e 'PGADMIN_DEFAULT_PASSWORD=$DB_PASSWORD' \
    -d dpage/pgadmin4:7.2
