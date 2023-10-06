#!/bin/bash

SECRET=
CLIENT_RESPONSE=

curl https://api.hcaptcha.com/siteverify \
  -X POST \
  -d "response=$CLIENT_RESPONSE&secret=$SECRET"
