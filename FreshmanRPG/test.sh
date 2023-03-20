#!/bin/bash

# macros for colors red, blue, green, white and reset
RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
WHITE='\033[0;37m'
RESET='\033[0m'

lastModule=0
printedTail=0

declare -a modules=("GameShared" "GameClient" "GameClient-desktop" "GameSequenceTests" "GameServer" "LoginServer")
declare -a tests=("edu.ship.engr.shipsim.AllSharedTests" "edu.ship.engr.shipsim.AllClientTests" "" "edu.ship.engr.shipsim.model.RunAllSequenceTests" "edu.ship.engr.shipsim.AllServerTests" "edu.ship.engr.shipsim.AllLoginServerTests")

declare -a table
for (( i=0; i<=18; i++ )); do
    table[$i]="waiting"
done

# function that converts an index to a cell coordinate
function indexToCell() {
    local index=$1

    local x=$(( ( index - 1 ) / 3 + 1 ))
    local y=$(( ( index - 1 ) % 3 + 1 ))

    echo "$x $y"
}

function printCell() {
    local x=$1
    local y=$2

    local index=$(( ( ( x - 1 ) * 3 ) + y ))

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

# function that prints table bars given a specific column position
function printBars() {
    local column=$1

    printf "├────────────────────┼──────────┼────────────┼──────────┤\n"
    printf "│     GameShared     │ %19s │ %21s │ %19s │\n" "$(printCell 1 $column)" "$(printCell 1 $(( column + 1 )))" "$(printCell 1 $(( column + 2 )))"
    printf "│     GameClient     │ %19s │ %21s │ %19s │\n" "$(printCell 2 $column)" "$(printCell 2 $(( column + 1 )))" "$(printCell 2 $(( column + 2 )))"
    printf "│ GameClient-desktop │ %19s │ %21s │ %19s │\n" "$(printCell 3 $column)" "$(printCell 3 $(( column + 1 )))" "$(printCell 3 $(( column + 2 )))"
    printf "│  GameSequenceTests │ %19s │ %21s │ %19s │\n" "$(printCell 4 $column)" "$(printCell 4 $(( column + 1 )))" "$(printCell 4 $(( column + 2 )))"
    printf "│      GameServer    │ %19s │ %21s │ %19s │\n" "$(printCell 5 $column)" "$(printCell 5 $(( column + 1 )))" "$(printCell 5 $(( column + 2 )))"
    printf "│     LoginServer    │ %19s │ %21s │ %19s │\n" "$(printCell 6 $column)" "$(printCell 6 $(( column + 1 )))" "$(printCell 6 $(( column + 2 )))"
}

# array with module names centered with spaces
modulesDisplay=( "    GameShared    " "    GameClient    " "GameClient-desktop" " GameSequenceTests" "     GameServer   " "    LoginServer   " )

function printElement() {
    local row=$1
    local column=$2

    # if column is 1
    if [[ $column == 1 ]]; then
        printf "│ %s │ %19s │" "${modulesDisplay[$(( row - 1 ))]}" "$(printCell "${row}" "${column}")"
    fi

    # if column is 2
    if [[ $column == 2 ]]; then
        printf " %21s │" "$(printCell "${row}" "${column}")"
    fi

    # if column is 3
    if [[ $column == 3 ]]; then
        printf " %19s │\n" "$(printCell "${row}" "${column}")"
    fi
}

function printRemainder() {
    for (( i=lastModule; i<=18; i++ )); do
        # variables for row and column
        local row=$(( ( i - 1 ) / 3 + 1 ))
        local column=$(( ( i - 1 ) % 3 + 1 ))

        printElement $row $column
    done

    # if last module is not 18
    if [[ $lastModule != 18 ]]; then
        printf "└────────────────────┴──────────┴────────────┴──────────┘\n"
        printedTail=1
    fi
}

function runTask() {
    # local variable for table index
    local index=$(( ( ( $1 - 1 ) * 3 ) + $2 ))
    local i=$1

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

    rc=$?

    lastModule=$index

    if [[ $rc -ne 0 ]]; then
        table[$index]="failed"

        # set remaining cells to skipped
        for (( i=$index+1; i<=18; i++ )); do
            table[$i]="skipped"
        done

        printRemainder

        printf "%s\n" "$output"

        return 1
    fi

    # set table cell to complete if it wasn't already set to skipped or failed
    if [[ ${table[$index]} != "skipped" && ${table[$index]} != "failed" ]]; then
        table[$index]="complete"
    fi

    # print element if skipped or complete
    if [[ ${table[$index]} == "skipped" || ${table[$index]} == "complete" ]]; then
        printElement $1 $2
    fi

    return 0
}

function run() {
    printf "┌────────────────────┬──────────┬────────────┬──────────┐\n"
    printf "│    Module Name     │ Compile  │ Checkstyle │  Testing │\n"
    printf "├────────────────────┼──────────┼────────────┼──────────┤\n"

    for (( i=1; i<=6; i++ )); do
        runTask "$i" 1 compileJava compileTestJava || break
        runTask "$i" 2 checkstyleMain checkstyleTest || break
        runTask "$i" 3 test || break
    done

    if [[ $printedTail -eq 0 ]]; then
        printf "└────────────────────┴──────────┴────────────┴──────────┘\n"
    fi
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

seedServer || exit
seedLogin || exit
seedShared || exit
seedServer2 || exit

run