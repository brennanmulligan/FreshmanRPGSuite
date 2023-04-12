FROM openjdk:17

COPY entrypoint.sh /watchdog-entrypoint.sh

RUN chmod +x /watchdog-entrypoint.sh

ENTRYPOINT '/watchdog-entrypoint.sh'