#!/bin/bash

rm -rf target project/target project/project/target

sbt clean compile run 

