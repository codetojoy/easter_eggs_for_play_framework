#!/bin/bash

set -e 

sbt clean dist
mkdir -p tmp
rm -rf tmp
mkdir tmp
cd tmp
cp ../target/universal/egg-exclude-1.0-SNAPSHOT.zip .
unzip egg-exclude-1.0-SNAPSHOT.zip 
cd egg-exclude-1.0-SNAPSHOT/lib
mkdir tmp
cd tmp
unzip ../egg-exclude.egg-exclude-1.0-SNAPSHOT-assets.jar 
ls -l 
pwd

echo "Ready."
