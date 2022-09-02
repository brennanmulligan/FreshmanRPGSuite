#!/usr/bin/env bash

cd /repo/frpg/CompanionAppServer/build || exit
mkdir -p /repo/frpg/logs/restfulserver/
java -jar RestfulServer.jar &>> /repo/frpg/logs/restfulserver/"$(date +"%Y-%m-%d")".log