#!/bin/bash

rm -rf ~/.ivy2
rm -rf ~/.sbt
rm -rf target project/target project/project/target

sbt clean  

