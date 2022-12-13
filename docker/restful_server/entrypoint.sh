#!/usr/bin/env bash

cd /repo/frpg/GameServer || exit
mkdir -p /repo/frpg/logs/restfulserver/
./../gradlew bootRun --args="--restfulServer" &>> /repo/frpg/logs/restfulserver/"$(date +"%Y-%m-%d")".log