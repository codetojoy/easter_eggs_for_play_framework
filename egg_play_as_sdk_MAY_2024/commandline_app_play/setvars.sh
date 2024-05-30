#!/bin/bash

export DB_USERNAME=CHANGE_ME
export DB_PASSWORD=CHANGE_ME
DB_NAME=CHANGE_ME
DB_PORT=5432
export DB_URL=jdbc:postgresql://127.0.0.1:$DB_PORT/$DB_NAME?ssl=false

