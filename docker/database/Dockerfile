FROM mysql:8.0

ENV DEBIAN_FRONTEND noninteractive

COPY init.sql /docker-entrypoint-initdb.d/init.sql

ENTRYPOINT ["bash", "/usr/local/bin/docker-entrypoint.sh", "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci"]