#!/bin/bash

# macros for colors red, blue, green, white and reset
RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
WHITE='\033[0;37m'
RESET='\033[0m'

center_string() {
    string="$1"
    max="$2"
    str_len=${#string}
    padding_len=$(( ($max - str_len) / 2 ))
    padding=$(printf '%*s' "$padding_len")
    printf '%s%s%s\n' "$padding" "$string" "$padding"
}

# array containing each module name
declare -a modules=("GameClient" "GameClient-desktop" "GameSequenceTests" "GameServer" "GameShared" "LoginServer")

# 30 cell array originally containing "waiting"
declare -a table
for (( i=0; i<=30; i++ )); do
    table[$i]="waiting"
done

# set index 16 to complete, 8 to running and 12 to failed
table[16]="complete"
table[8]="running"
table[12]="failed"

# function that prints calculates an index for two coordinates, then prints the value at that index in the table array
function printCell() {
    local x=$1
    local y=$2

    local index=$(( ( ( x - 1 ) * 3 ) + y ))


    # if the cell is waiting, print it in white
    # if the cell is running, print it in blue
    # if the cell is complete, print it in green
    # if the cell is failed, print it in red
    # reset color after printing
    # print it all in one line
    if [[ ${table[$index]} == "waiting" ]]; then
        echo -ne "${WHITE}${table[$index]}${RESET}"
    elif [[ ${table[$index]} == "running" ]]; then
        echo -ne "${BLUE}${table[$index]}${RESET}"
    elif [[ ${table[$index]} == "complete" ]]; then
        echo -ne "${GREEN}${table[$index]}${RESET}"
    elif [[ ${table[$index]} == "failed" ]]; then
        echo -ne "${RED}${table[$index]}${RESET}"
    fi
}

# function that prints a 2x10 table, with each cell running the printRunning function
function printTable() {
    # clear terminal
    clear

    # move cursor to top left of terminal
    echo -ne "\033[0;0H"

    # print header separator
    echo "┌────────────────────┬──────────┬────────────┬──────────┐"

    # print table header
    echo "│    Module Name     │ Compile  │ Checkstyle │  Testing │"

    # print header separator
    echo "├────────────────────┼──────────┼────────────┼──────────┤"

    printf "│     GameClient     │ %19s │ %21s │ %19s │\n" "$(printCell 1 1)" "$(printCell 1 2)" "$(printCell 1 3)"
    printf "│ GameClient-desktop │ %19s │ %21s │ %19s │\n" "$(printCell 2 1)" "$(printCell 2 2)" "$(printCell 2 3)"
    printf "│ GameSequenceTests  │ %19s │ %21s │ %19s │\n" "$(printCell 3 1)" "$(printCell 3 2)" "$(printCell 3 3)"
    printf "│     GameServer     │ %19s │ %21s │ %19s │\n" "$(printCell 4 1)" "$(printCell 4 2)" "$(printCell 4 3)"
    printf "│     GameShared     │ %19s │ %21s │ %19s │\n" "$(printCell 5 1)" "$(printCell 5 2)" "$(printCell 5 3)"
    printf "│     LoginServer    │ %19s │ %21s │ %19s │\n" "$(printCell 6 1)" "$(printCell 6 2)" "$(printCell 6 3)"

    # print footer separator
    echo "└────────────────────┴──────────┴────────────┴──────────┘"
}

printTable