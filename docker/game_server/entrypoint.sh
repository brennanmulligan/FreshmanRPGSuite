#!/usr/bin/env bash

cd /repo/frpg/GameServer || exit
cp build/GameServer.jar .
sleep 5
java -jar GameServer.jar --port="$FRPG_PORT" --map="$FRPG_MAP"