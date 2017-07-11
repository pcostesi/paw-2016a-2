#!/bin/bash

set -e

PAMPERO="pampero.itba.edu.ar"
GROUP="grupo2"
PASS="shiufi7T"
PAWSERVER="10.16.1.110"
APPWAR="app.war"

function build_app() {
    mvn clean package
    cp webapp/target/webapp.war "${APPWAR}"
    chmod +rwx "${APPWAR}"
}

function upload_app() {
    echo "If prompted, password for group ${GROUP} is ${PASS}"
    sftp -o "ProxyJump ${PAMPERO}" "${GROUP}@${PAWSERVER}" <<_EOF
rm web/tmp-${APPWAR}
rm web/${APPWAR}
put ${APPWAR} web/tmp-${APPWAR}
rename web/tmp-${APPWAR} web/${APPWAR}
bye
_EOF
}

function save_db() {
    DATETIME=$(date +%s)
    DUMP="${GROUP}-${DATETIME}.sql"
    ssh "${PAMPERO}" <<_EOF
    export PGPASSWORD="${PASS}"
    pg_dump -U ${GROUP} -h ${PAWSERVER} > ${DUMP}
_EOF
    scp "${PAMPERO}":"${DUMP}" .
}

function all() {
    build_app;
    save_db;
    upload_app;
}

function main() {
    case "${1}" in
        dump)
        save_db;
        ;;
        build)
        build_app;
        ;;
        *)
        all;
        ;;
    esac
}

main $@;
