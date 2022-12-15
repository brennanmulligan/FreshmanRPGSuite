FROM openjdk:11.0.11

COPY entrypoint.sh /game-server-entrypoint.sh

RUN chmod +x /game-server-entrypoint.sh

ENTRYPOINT '/game-server-entrypoint.sh'