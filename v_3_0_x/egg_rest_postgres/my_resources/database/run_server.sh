#!/bin/bash

docker run -p 127.0.0.1:5432:5432 -e POSTGRES_PASSWORD="TODO" --name my-pg postgres:latest
