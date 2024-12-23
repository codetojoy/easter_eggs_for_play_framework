#!/bin/bash

MY_PID=`ps -ef | grep -v grep | grep "address.*9999" | awk '{print $2}'`
echo "MY_PID : ${MY_PID}"

if [ ! -z "${MY_PID}" ]; then
    kill $MY_PID
fi
