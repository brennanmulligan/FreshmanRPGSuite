#!/usr/bin/env bash

cd /repo/frpg/GameServer || exit
mkdir -p /repo/frpg/logs/restfulserver/
./../gradlew bootRun --args="--restfulServer" &>> /repo/frpg/logs/restfulserver/"$(date +"%Y-%m-%d")".log
#java -jar GameServer.jar --map="RestfulMap.tmx" --port="1890" &>> /repo/frpg/logs/restfulserver/"$(date +"%Y-%m-%d")".log