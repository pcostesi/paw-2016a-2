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

function build_docker_db() {
    docker run --name pawdb -e POSTGRES_PASSWORD="${GROUP}" -e POSTGRES_USER="${GROUP}" -e POSTGRES_DB="${GROUP}" -d postgres:9.3
}

function restore_to_docker_db() {
    LOCAL_DUMP_PATH="./dumps/$(ls ./dumps | sort -r | head -n 1)"
    export PGPASSWORD="${GROUP}"
    docker exec -i pawdb psql -U "${GROUP}" -d "${GROUP}" < "${LOCAL_DUMP_PATH}"
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
    pg_dump -U ${GROUP} -h ${PAWSERVER} -E "UTF-8" -o --clean > ${DUMP}
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
        docker)
        build_docker_db
        ;;
        dump)
        save_db;
        ;;
        build)
        build_app;
        ;;
        load)
        restore_to_docker_db
        ;;
        restore_to_docker)
        restore_to_docker_db
        ;;
        upload)
        upload_app;
        ;;
        *)
        all;
        ;;
    esac
}

main "$@";
