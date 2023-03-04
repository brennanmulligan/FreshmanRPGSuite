#!/usr/bin/env bash

function search() {
    ./gradlew checkForDuplicateClasses || exit
}

function seedServer() {
    cd GameServer > /dev/null || exit
    ./../gradlew DBBuildTestQuestsAndObjectives DBBuildTestDBPlayers DBBuildTestLevels DBBuildTestQuizbotQuestions DBBuildTestInteractableItems DBBuildTestVanityItems --console=plain || exit
    cd - > /dev/null || exit
}

function seedLogin() {
    cd LoginServer > /dev/null || exit
    ./../gradlew DBBuildTestDBPlayerLogin --console=plain || exit
    cd - > /dev/null || exit
}

function seedShared() {
    cd GameShared > /dev/null || exit
    ./../gradlew DBBuildTestDBServers --console=plain || exit
    cd - > /dev/null || exit
}

function seedServer2() {
    cd GameServer > /dev/null || exit
    ./../gradlew DBBuildTestDoubloonPrizes DBBuildTestRandomFacts DBBuildTestDBVisitedMaps DBBuildTestFriends DBBuildTestVanityInventory DBBuildTestDefaultItems DBBuildTestVanityAwards DBBuildTestVanityShop --console=plain || exit
    cd - > /dev/null || exit
}

function testGameShared() {
    cd GameShared > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.AllSharedTests" --console=plain || exit
    cd - > /dev/null || exit
}

function testLoginServer() {
    cd LoginServer > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.AllLoginServerTests" --console=plain || exit
    cd - > /dev/null || exit
}

function testGameServer() {
    cd GameServer > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.AllServerTests" --console=plain || exit
    cd - > /dev/null || exit
}

function testGameClient() {
    cd GameClient > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.AllClientTests" --console=plain || exit
    cd - > /dev/null || exit
}

function testGameSequenceTests() {
    cd GameSequenceTests > /dev/null || exit
    ./../gradlew test --tests "edu.ship.engr.shipsim.model.RunAllSequenceTests" --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameClient() {
    cd GameClient > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameClientDesktop() {
    cd GameClient-desktop > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameManager() {
    cd GameManager > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameSequenceTests() {
    cd GameSequenceTests > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameServer() {
    cd GameServer > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --console=plain || exit
    cd - > /dev/null || exit
}

function checkGameShared() {
    cd GameShared > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --console=plain || exit
    cd - > /dev/null || exit
}

function checkLoginServer() {
    cd LoginServer > /dev/null || exit
    ./../gradlew checkstyleMain checkstyleTest --console=plain || exit
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
}

function runCheckstyle() {
    checkGameClient
    checkGameClientDesktop
    checkGameSequenceTests
    checkGameServer
    checkGameShared
    checkLoginServer
}

function run() {
    search
    seedFirst
    seedSecond
    runTests
    runCheckstyle
}

run "$1"