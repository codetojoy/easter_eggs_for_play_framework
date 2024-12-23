#!/bin/bash

MY_PID=`ps -ef | grep -v grep | grep "address.*9999" | awk '{print $2}'`

if [ -z "${MY_PID}" ]; then
    echo "PID not found"
    exit 1
else
    echo "PID is ${MY_PID}"
    exit 0
fi
