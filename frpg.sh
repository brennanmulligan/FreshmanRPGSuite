#!/usr/bin/env bash

basedir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
self_name="${0##*/}"

setup() {
    NAME="frpg"
    if [[ -n "$1" ]] ; then
        NAME="$1"
    fi

    echo 'You can run this command to add the alias to your ~/.bashrc file'
    echo "Command: "
    echo "echo \"alias $NAME='. $basedir/$self_name'\" >> ~/.bashrc"
}

up() {
    cwd=$(pwd)

    docker compose -f "$basedir/docker/docker-compose.yml" build &>/dev/null

    HOST=$(hostname)
    if [[ "$HOST" == "rpgserv" ]] ; then
        cd "$basedir/FreshmanRPG/GameServer" &>/dev/null
        ./../gradlew build -x test
        cd "$basedir/FreshmanRPG/LoginServer" &>/dev/null
        ./../gradlew build -x test
        cd "$basedir/FreshmanRPG/CompanionAppServer" &>/dev/null
        ./../gradlew build -x test

        docker compose -f "$basedir/docker/docker-compose.yml" up -d
    else
        docker compose -f "$basedir/docker/docker-compose.yml" up -d frpg_mysql
    fi

    cd "$cwd" || exit
}

down() {
    if [[ -n "$1" ]]; then
        docker compose -f "$basedir/docker/docker-compose.yml" rm -s -v "$1"
    else
        docker compose -f "$basedir/docker/docker-compose.yml" down
    fi
}

create_clients() {
    cwd=$(pwd)

    echo "Generating Linux Client"
    cd "$basedir/FreshmanRPG/GameClient-desktop" || exit
    cd "src/main/resources" || exit
    zip -qq -r resources.zip . -x "resources.zip"
    cd - &>/dev/null || exit
    ./../gradlew shadowJar
    rm "src/main/resources/resources.zip" || exit

    cd "$cwd" || exit
}

print_help() {
    echo '┌─────────────────────────────────────────────────────────────────────────────────────────────────┐'
    echo '│ This is the utility script for the FreshmanRPG project. For all functionality of                │'
    echo '│ this script to be available, you must first run the "setup" command.                            │'
    echo '├──────────────────────────────────────────────────────┬──────────────────────────────────────────┤'
    echo '│ These commands require the setup command before use: │                                          │'
    echo '├──────────────────┬───────────────────────────────────┘                                          │'
    echo '│ * c, compose     │ Shortcut for docker-compose command                                          │'
    echo '│ * u, up          │ Start all of the docker containers                                           │'
    echo '│ * d, down        │ Stop docker containers                                                       │'
    echo '│                  │                                                                              │'
    echo '│ * r, root        │ Change directory to the root of the project.                                 │'
    echo '├──────────────────┴───────────────────────────┬──────────────────────────────────────────────────┤'
    echo '│ These commands are for maintenance use only: │                                                  │'
    echo '├──────────────────┬───────────────────────────┘                                                  │'
    echo '│ * clients        │ Generate GameClient-desktop launchers                                        │'
    echo '├──────────────────┼──────────────────────────────────────────────────────────────────────────────┤'
    echo '│ * setup          │ Add an alias to allow full functionality of this script. Run as:             │'
    echo '│                  │     ./frpg.sh setup                                                          │'
    echo '│                  │ After this, it will give you a command to execute.                           │'
    echo '│                  │ This command will add the alias to your alias file                           │'
    echo '│                  │                                                                              │'
    echo '│                  │ The default for the resulting alias is "frpg", but you can give an           │'
    echo '│                  │ argument to override this default. Such as:                                  │'
    echo '│                  │     ./frpg.sh setup example                                                  │'
    echo '│                  │ Which will allow you to run "example" instead.                               │'
    echo '└──────────────────┴──────────────────────────────────────────────────────────────────────────────┘'
}

case "$1" in
    "r" | "root")
        cd "$basedir" || exit
    ;;
    "c" | "compose")
        docker compose -f "$basedir/docker/docker-compose.yml" "${@:2}"
    ;;
    "u" | "up")
        up
    ;;
    "d" | "down")
        down "${@:2}"
    ;;
    "clients")
        create_clients
    ;;
    "setup")
        setup "$2"
    ;;
    *)
        print_help
    ;;
esac