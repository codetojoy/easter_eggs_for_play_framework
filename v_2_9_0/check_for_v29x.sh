#!/bin/bash

echo "checking Java for HttpExecutionContext ..."
find $1 -name "*.java" -exec grep -l HttpExecutionContext {} \; 

echo "checking Java for javax.persistence ..."
find $1 -name "*.java" -exec grep -l "javax.persistence" {} \; 

echo "checking xml for javax.persistence ..."
find $1 -name "*.xml" -exec grep -l "javax.persistence" {} \; 

echo "Ready."
