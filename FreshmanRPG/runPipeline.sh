#!/usr/bin/env bash

function seedServer() {
    cd GameServer > /dev/null || exit
    ./../gradlew DBBuildTestQuestsAndObjectives DBBuildTestDBPlayers DBBuildTestLevels DBBuildTestQuizbotQuestions DBBuildTestInteractableItems DBBuildTestTimer DBBuildTestVanityItems --console=plain || exit
    cd - > /dev/null || exit
}

function seedLogin() {
    cd LoginServer > /dev/null || exit
    ./../gradlew DBBuildTestDBPlayerLogin --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function seedShared() {
    cd GameShared > /dev/null || exit
    ./../gradlew DBBuildTestDBServers --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function seedServer2() {
    cd GameServer > /dev/null || exit
    ./../gradlew DBBuildTestDoubloonPrizes DBBuildTestRandomFacts DBBuildTestDBVisitedMaps DBBuildTestFriends DBBuildTestVanityInventory DBBuildTestDefaultItems DBBuildTestVanityAwards DBBuildTestVanityShop DBBuildTestCrew DBBuildTestMajor --console=plain || exit
    cd - > /dev/null || exit
}

function testGameShared() {
    cd GameShared > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.AllSharedTests" --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function testLoginServer() {
    cd LoginServer > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.AllLoginServerTests" --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function testGameServer() {
    cd GameServer > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.AllServerTests" --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function testGameClient() {
    cd GameClient > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.AllClientTests" --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function testGameSequenceTests() {
    cd GameSequenceTests > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.model.RunAllSequenceTests" --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function testWatchdog() {
    cd Watchdog > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.AllWatchdogTests" --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameClient() {
    cd GameClient > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameClientDesktop() {
    cd GameClient-desktop > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameManager() {
    cd GameManager > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameSequenceTests() {
    cd GameSequenceTests > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameServer() {
    cd GameServer > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameShared() {
    cd GameShared > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function checkLoginServer() {
    cd LoginServer > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function checkWatchdog() {
    cd Watchdog > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --build-cache --console=plain || exit
    cd - > /dev/null || exit
}

function seedFirst() {
    seedServer
    seedLogin
    seedShared
}

function seedSecond() {
    seedServer2
}

function runTests() {
    testGameShared
    testLoginServer
    testGameServer
    testGameClient
    testGameSequenceTests
    testWatchdog
}

function runCheckstyle() {
    checkGameClient
    checkGameClientDesktop
    checkGameSequenceTests
    checkGameServer
    checkGameShared
    checkLoginServer
    checkWatchdog
}

function run() {
    seedFirst
    seedSecond
    runTests
    runCheckstyle
}

run "$1"