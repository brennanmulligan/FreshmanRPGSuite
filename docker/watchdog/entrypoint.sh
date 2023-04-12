#!/usr/bin/env bash

cd /repo/frpg/Watchdog || exit
cp build/Watchdog.jar .
sleep 5
mkdir -p /repo/frpg/logs/watchdog
java -jar Watchdog.jar &>> /repo/frpg/logs/watchdog/"$(date +"%Y-%m-%d")".log