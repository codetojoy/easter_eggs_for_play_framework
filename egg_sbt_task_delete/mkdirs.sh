#!/bin/bash

mkdir -p conf/dev_resources/foo/bar

touch conf/dev_resources/index.html
touch conf/dev_resources/foo/README.md
touch conf/dev_resources/foo/Foo.groovy
touch conf/dev_resources/foo/bar/README.md
touch conf/dev_resources/foo/bar/Bar.groovy

find conf -name "*.groovy"
