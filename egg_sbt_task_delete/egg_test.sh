#!/bin/bash

./mkdirs.sh

sbt clean compile

ls -lrt ./conf

echo "Ready."
