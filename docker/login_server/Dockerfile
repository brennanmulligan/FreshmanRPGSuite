FROM openjdk:11

COPY entrypoint.sh /login-server-entrypoint.sh

RUN chmod +x /login-server-entrypoint.sh

ENTRYPOINT '/login-server-entrypoint.sh'