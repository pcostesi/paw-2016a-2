#!/bin/bash

set -e

PAMPERO="pampero.itba.edu.ar"
GROUP="grupo2"
PASS="shiufi7T"
PAWSERVER="10.16.1.110"
APPWAR="app.war"

function build_app() {
    mkdir -p deploy
    mvn clean package
    cp webapp/target/webapp.war "deploy/${APPWAR}"
    chmod +rwx "deploy/${APPWAR}"
}

function upload_app() {
    echo "If prompted, password for group ${GROUP} is ${PASS}"
    sftp -o "ProxyJump ${PAMPERO}" "${GROUP}@${PAWSERVER}" <<_EOF
rm tmp-${APPWAR}
put ./deploy/${APPWAR} web/tmp-${APPWAR}
rm web/${APPWAR}
rename web/tmp-${APPWAR} web/${APPWAR}
bye
_EOF
}

function save_db() {
    mkdir -p dumps
    DATETIME=$(date +%s)
    DUMP="${GROUP}-${DATETIME}.sql"
    ssh "${PAMPERO}" << _EOF
    export PGPASSWORD="${PASS}"
    pg_dump -U ${GROUP} -h ${PAWSERVER} > ${DUMP}
_EOF
    scp "${PAMPERO}":"${DUMP}" ./dumps
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
        deploy)
        upload_app;
        ;;
        *)
        all;
        ;;
    esac
}

main "$@";
