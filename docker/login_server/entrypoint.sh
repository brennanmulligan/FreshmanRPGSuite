#!/usr/bin/env bash

cd /repo/frpg/LoginServer || exit
cp build/LoginServer.jar .
java -jar LoginServer.jar