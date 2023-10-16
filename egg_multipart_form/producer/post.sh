#!/bin/bash

curl --trace-ascii - -F "foo=bar" -F "name=evh" http://localhost:5150/consume
