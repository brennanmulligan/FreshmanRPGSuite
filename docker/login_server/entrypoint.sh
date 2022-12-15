#!/usr/bin/env bash

cd /repo/frpg/LoginServer || exit
cp build/LoginServer.jar .
mkdir -p /repo/frpg/logs/loginserver/
java -jar LoginServer.jar &>> /repo/frpg/logs/loginserver/"$(date +"%Y-%m-%d")".log