#!/bin/bash

# macros for colors red, blue, green, white and reset
RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
WHITE='\033[0;37m'
RESET='\033[0m'

lastModule=0
lastSeed=0
printedModuleTail=0
printedSeedTail=0

# array with module names centered with spaces
declare -a modulesDisplay=( "    GameShared    " "    GameClient    " "GameClient-desktop" " GameSequenceTests" "     GameServer   " "    LoginServer   " )
declare -a modules=("GameShared" "GameClient" "GameClient-desktop" "GameSequenceTests" "GameServer" "LoginServer")
declare -a tests=("edu.ship.engr.shipsim.AllSharedTests" "edu.ship.engr.shipsim.AllClientTests" "" "edu.ship.engr.shipsim.model.RunAllSequenceTests" "edu.ship.engr.shipsim.AllServerTests" "edu.ship.engr.shipsim.AllLoginServerTests")

declare -a table
for (( i=0; i<=18; i++ )); do
    table[$i]="waiting"
done

declare -a seedTable=("waiting" "waiting" "waiting" "waiting")

# function that converts an index to a cell coordinate
function indexToCell() {
    local index=$1

    local x=$(( ( index - 1 ) / 3 + 1 ))
    local y=$(( ( index - 1 ) % 3 + 1 ))

    echo "$x $y"
}

function printTestCell() {
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

function printSeedCell() {
    if [[ ${seedTable[$1]} == "waiting" ]]; then
        printf "${WHITE}%s${RESET}" "${seedTable[$1]}"
    elif [[ ${seedTable[$1]} == "running" ]]; then
        printf "${BLUE}%s${RESET}" "${seedTable[$1]}"
    elif [[ ${seedTable[$1]} == "skipped" ]]; then
        printf "${BLUE}%s${RESET}" "${seedTable[$1]}"
    elif [[ ${seedTable[$1]} == "complete" ]]; then
        printf "${GREEN}%s${RESET}" "${seedTable[$1]}"
    elif [[ ${seedTable[$1]} == "failed" ]]; then
        printf "${RED}%s${RESET}" "${seedTable[$1]}"
    fi
}

function printElement() {
    local row=$1
    local column=$2

    # if column is 1
    if [[ $column == 1 ]]; then
        printf "│ %s │ %19s │" "${modulesDisplay[$(( row - 1 ))]}" "$(printTestCell "${row}" "${column}")"
    fi

    # if column is 2
    if [[ $column == 2 ]]; then
        printf " %21s │" "$(printTestCell "${row}" "${column}")"
    fi

    # if column is 3
    if [[ $column == 3 ]]; then
        printf " %19s │\n" "$(printTestCell "${row}" "${column}")"
    fi
}

function printSeedElement() {
    local column=$1

    # if column is 1
    if [[ $column == 1 ]]; then
        printf "│ %22s │" "$(printSeedCell "${column}")"
    fi

    # if column is 2
    if [[ $column == 2 ]]; then
        printf " %27s │" "$(printSeedCell "${column}")"
    fi

    # if column is 3
    if [[ $column == 3 ]]; then
        printf " %26s │" "$(printSeedCell "${column}")"
    fi

    # if column is 4
    if [[ $column == 4 ]]; then
        printf " %24s │\n" "$(printSeedCell "${column}")"
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
        printedModuleTail=1
    fi
}

function printSeedRemainder() {
    for (( i=lastSeed; i<=4; i++ )); do
        printSeedElement "$i"
    done

    # if last module is not 18
    if [[ $lastSeed != 4 ]]; then
        printf "└─────────────┴──────────────────┴─────────────────┴───────────────┘\n"
        printedSeedTail=1
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

        exit 1
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

function runSeed() {
    # local variable for table index
    local index=$1
    local module="$2"
    local tasks="${*:3}"

    cd "$module" > /dev/null
    output=$(./../gradlew --build-cache "$tasks" 2>&1)
    cd - > /dev/null

    rc=$?

    lastSeed=$index

    if [[ $rc -ne 0 ]]; then
        seedTable[$index]="failed"

        # set remaining cells to skipped
        for (( i=$index+1; i<=4; i++ )); do
            seedTable[$i]="skipped"
        done

        printSeedRemainder

        printf "%s\n" "$output"

        exit 1
    fi

    # set table cell to complete if it wasn't already set to skipped or failed
    if [[ ${seedTable[$index]} != "skipped" && ${seedTable[$index]} != "failed" ]]; then
        seedTable[$index]="complete"
    fi

    # print element if skipped or complete
    if [[ ${seedTable[$index]} == "skipped" || ${seedTable[$index]} == "complete" ]]; then
        printSeedElement $1
    fi

    return 0
}

function runSeeds() {
    runSeed 1 "GameServer" DBBuildTestQuestsAndObjectives DBBuildTestDBPlayers DBBuildTestLevels DBBuildTestQuizbotQuestions DBBuildTestInteractableItems DBBuildTestVanityItems || return 1
    runSeed 2 "LoginServer" DBBuildTestDBPlayerLogin || return 1
    runSeed 3 "GameShared" DBBuildTestDBServers || return 1
    runSeed 4 "GameServer" DBBuildTestDoubloonPrizes DBBuildTestRandomFacts DBBuildTestDBVisitedMaps DBBuildTestFriends DBBuildTestVanityInventory DBBuildTestDefaultItems DBBuildTestVanityAwards DBBuildTestVanityShop || return 1

    return 0
}

function run() {
    printf "┌─────────────┬──────────────────┬─────────────────┬───────────────┐\n"
    printf "│ Seed Server │ Seed LoginServer │ Seed GameShared │ Seed Server 2 │\n"
    printf "├─────────────┼──────────────────┼─────────────────┼───────────────┤\n"

    runSeeds

    if [[ $printedSeedTail -eq 0 ]]; then
        printf "└─────────────┴──────────────────┴─────────────────┴───────────────┘\n"
    fi

    printf "┌────────────────────┬──────────┬────────────┬──────────┐\n"
    printf "│    Module Name     │ Compile  │ Checkstyle │  Testing │\n"
    printf "├────────────────────┼──────────┼────────────┼──────────┤\n"

    for (( i=1; i<=6; i++ )); do
        runTask "$i" 1 compileJava compileTestJava || break
        runTask "$i" 2 checkstyleMain checkstyleTest || break
        runTask "$i" 3 test || break
    done

    if [[ $printedModuleTail -eq 0 ]]; then
        printf "└────────────────────┴──────────┴────────────┴──────────┘\n"
    fi
}

run