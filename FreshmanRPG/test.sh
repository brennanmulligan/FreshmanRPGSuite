#!/bin/bash

# macros for colors red, blue, green, white and reset
RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
WHITE='\033[0;37m'
RESET='\033[0m'

# array containing each module name
declare -a modules=("GameShared" "GameClient" "GameClient-desktop" "GameSequenceTests" "GameServer" "LoginServer")
declare -a tests=("edu.ship.engr.shipsim.AllSharedTests" "edu.ship.engr.shipsim.AllClientTests" "" "edu.ship.engr.shipsim.model.RunAllSequenceTests" "edu.ship.engr.shipsim.AllServerTests" "edu.ship.engr.shipsim.AllLoginServerTests")

# 30 cell array originally containing "waiting"
declare -a table
for (( i=0; i<=30; i++ )); do
    table[$i]="waiting"
done

# function that prints calculates an index for two coordinates, then prints the value at that index in the table array
function printCell() {
    local x=$1
    local y=$2

    local index=$(( ( ( x - 1 ) * 3 ) + y ))


    # if the cell is waiting, print it in white
    # if the cell is running, print it in blue
    # if the cell is skipped, print it in blue
    # if the cell is complete, print it in green
    # if the cell is failed, print it in red
    # reset color after printing
    # print it all in one line
    if [[ ${table[$index]} == "waiting" ]]; then
        printf "${WHITE}%s${RESET}" "${table[$index]}"
    elif [[ ${table[$index]} == "running" ]]; then
        printf "${BLUE}%s${RESET}" "${table[$index]}"
    elif [[ ${table[$index]} == "skipped" ]]; then
        printf "${BLUE}%s${RESET}" "${table[$index]}"
    elif [[ ${table[$index]} == "complete" ]]; then
        printf "${GREEN}%s${RESET}" "${table[$index]}"
    elif [[ ${table[$index]} == "failed" ]]; then
        printf "${RED}%s${RESET}" "${table[$index]}"
    fi
}

# function that prints a 2x10 table, with each cell running the printRunning function
function printTable() {
    # move cursor to top left of terminal
    echo -ne "\033[0;0H"

    printf "┌────────────────────┬──────────┬────────────┬──────────┐\n"
    printf "│    Module Name     │ Compile  │ Checkstyle │  Testing │\n"
    printf "├────────────────────┼──────────┼────────────┼──────────┤\n"
    printf "│     GameShared     │ %19s │ %21s │ %19s │\n" "$(printCell 1 1)" "$(printCell 1 2)" "$(printCell 1 3)"
    printf "│     GameClient     │ %19s │ %21s │ %19s │\n" "$(printCell 2 1)" "$(printCell 2 2)" "$(printCell 2 3)"
    printf "│ GameClient-desktop │ %19s │ %21s │ %19s │\n" "$(printCell 3 1)" "$(printCell 3 2)" "$(printCell 3 3)"
    printf "│  GameSequenceTests │ %19s │ %21s │ %19s │\n" "$(printCell 4 1)" "$(printCell 4 2)" "$(printCell 4 3)"
    printf "│      GameServer    │ %19s │ %21s │ %19s │\n" "$(printCell 5 1)" "$(printCell 5 2)" "$(printCell 5 3)"
    printf "│      LoginServer   │ %19s │ %21s │ %19s │\n" "$(printCell 6 1)" "$(printCell 6 2)" "$(printCell 6 3)"

    # print footer separator
    echo "└────────────────────┴──────────┴────────────┴──────────┘"
}

function runTask() {
    # local variable for table index
    local index=$(( ( ( $1 - 1 ) * 3 ) + $2 ))
    local i=$1

    # set table cell to running
    table[$index]="running"

    # print table
    printTable

    if [[ $2 == 1 || $2 == 2 ]]; then
        if [[ ${modules[$i-1]} == "GameClient-desktop" ]]; then
            output=$(./gradlew --build-cache "${modules[$i-1]}:$3" 2>&1)
        else
            output=$(./gradlew --build-cache "${modules[$i-1]}:$3" "${modules[$i-1]}:$4" 2>&1)
        fi
    else
        if [[ ${modules[$i-1]} != "GameClient-desktop" ]]; then
            output=$(./gradlew --build-cache "${modules[$i-1]}:$3" --tests "${tests[$i-1]}" 2>&1)
        else
            # set table cell to skipped
            table[$index]="skipped"
        fi
    fi

    if [[ $? -ne 0 ]]; then
        table[$index]="failed"
        printTable

        printf "%s\n" "$output"

        return 1
    fi

    # set table cell to complete if it wasn't already set to skipped or failed
    if [[ ${table[$index]} != "skipped" && ${table[$index]} != "failed" ]]; then
        table[$index]="complete"
    fi

    printTable

    return 0
}

function run() {
    printTable

    for (( i=1; i<=6; i++ )); do
        runTask "$i" 1 compileJava compileTestJava || break
        runTask "$i" 2 checkstyleMain checkstyleTest || break
        runTask "$i" 3 test || break
    done
}

function seedServer() {
    cd GameServer > /dev/null || exit
    ./../gradlew --build-cache DBBuildTestQuestsAndObjectives DBBuildTestDBPlayers DBBuildTestLevels DBBuildTestQuizbotQuestions DBBuildTestInteractableItems DBBuildTestVanityItems --console=plain || exit
    cd - > /dev/null || exit
}

function seedLogin() {
    cd LoginServer > /dev/null || exit
    ./../gradlew --build-cache DBBuildTestDBPlayerLogin --console=plain || exit
    cd - > /dev/null || exit
}

function seedShared() {
    cd GameShared > /dev/null || exit
    ./../gradlew --build-cache DBBuildTestDBServers --console=plain || exit
    cd - > /dev/null || exit
}

function seedServer2() {
    cd GameServer > /dev/null || exit
    ./../gradlew --build-cache DBBuildTestDoubloonPrizes DBBuildTestRandomFacts DBBuildTestDBVisitedMaps DBBuildTestFriends DBBuildTestVanityInventory DBBuildTestDefaultItems DBBuildTestVanityAwards DBBuildTestVanityShop --console=plain || exit
    cd - > /dev/null || exit
}

seedServer 2>&1 /dev/null || exit
seedLogin 2>&1 /dev/null || exit
seedShared 2>&1 /dev/null || exit
seedServer2 2>&1 /dev/null || exit

clear
run