#!/usr/bin/env bash

cd /repo/frpg/GameServer || exit
cp build/GameServer.jar .
sleep 5
mkdir -p /repo/frpg/logs/gameserver/"$FRPG_MAP"
java -jar GameServer.jar --port="$FRPG_PORT" --map="$FRPG_MAP" &>> /repo/frpg/logs/gameserver/"$FRPG_MAP"/"$(date +"%Y-%m-%d")".log