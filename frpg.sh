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

setup() {
    NAME="frpg"
    if [[ -n "$1" ]]; then
        NAME="$1"
    fi

    echo "You can run this command to add the alias to your ~/.${shellname}rc file"
    echo "Command: "
    echo "echo \"alias $NAME='. $basedir/$self_name'\" >> ~/.${shellname}rc"
}

run_compose() {
    HOST=$(hostname)

    if [[ -v FRPG_PROD || "$HOST" == "rpgserv" ]]; then
        docker compose -f "$basedir/docker/prod-docker-compose.yml" "${@:1}"
    else
        docker compose -f "$basedir/docker/dev-docker-compose.yml" "${@:1}"
    fi
}

up() {
    cwd=$(pwd)

    run_compose build &>/dev/null

    HOST=$(hostname)
    if [[ -v FRPG_PROD || "$HOST" == "rpgserv" ]]; then
        cd "$basedir/FreshmanRPG/GameServer" &>/dev/null || exit
        ./../gradlew build -x test
        cd "$basedir/FreshmanRPG/LoginServer" &>/dev/null || exit
        ./../gradlew build -x test
        cd "$basedir/FreshmanRPG/Watchdog" &>/dev/null || exit
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
    "setup")
        setup "$2"
    ;;
    *)
        print_help
    ;;
esac
