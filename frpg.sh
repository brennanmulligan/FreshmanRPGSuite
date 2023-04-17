#!/bin/bash

base_dir() {
    case "$SHELL" in
    "/bin/bash")
        basedir=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
        ;;
    "/bin/zsh")
        basedir=$(cd -P -- "$(dirname -- "$0")" && printf '%s\n' "$(pwd -P)")
        ;;
    esac
}

shell_name() {
    case "$SHELL" in
    "/bin/bash")
        shellname='bash'
        ;;
    "/bin/zsh")
        shellname='zsh'
        ;;
    esac
}

base_dir
shell_name
self_name="${0##*/}"
parentdir="$basedir/../"

setup() {
    NAME="frpg"
    if [[ -n "$1" ]]; then
        NAME="$1"
    fi

    echo 'You can run this command to add the alias to your ~/.bashrc file'
    echo "Command: "
    echo "echo \"alias $NAME='. $basedir/$self_name'\" >> ~/.bashrc"
}

run_compose() {
    HOST=$(hostname)

    if [[ -v FRPG_PROD || "$HOST" == "rpgserv" ]]; then
        docker compose -f "$parentdir/docker/prod-docker-compose.yml" "${@:1}"
    else
        docker compose -f "$parentdir/docker/dev-docker-compose.yml" "${@:1}"
    fi
}

up() {
    cwd=$(pwd)

    run_compose build &>/dev/null

    HOST=$(hostname)
    if [[ -v FRPG_PROD || "$HOST" == "rpgserv" ]]; then
        cd "$basedir/GameServer" &>/dev/null || exit
        ./../gradlew build -x test
        cd "$basedir/LoginServer" &>/dev/null || exit
        ./../gradlew build -x test
    fi

    run_compose up -d

    cd "$cwd" || exit
}

down() {
    if [[ -n "$1" ]]; then
        run_compose rm -s -v "$1"
    else
        run_compose down
    fi
}

restart() {
    down
    up
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

    scp -pC "$basedir/FreshmanRPG/GameClient-desktop/build/GameClient-linux.jar" "rpgadmin@rpgserv.engr.ship.edu:/var/www/html/static/GameClient-linux.jar"

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
        run_compose "${@:2}"
    ;;
    "u" | "up")
        up
    ;;
    "d" | "down")
        down "${@:2}"
    ;;
    "restart")
        restart
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
