#!/bin/bash

set -e

echo "clean ..."
groovy SQL_Clean.groovy 

echo "create ..."
groovy SQL_Create.groovy 

echo "populate ..."
time groovy SQL_Populate.groovy 

echo "read ..."
time groovy SQL_READ.groovy 

echo "Ready."

