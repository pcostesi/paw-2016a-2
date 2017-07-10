#!/bin/bash

set -e

PAMPERO="pampero.itba.edu.ar"
GROUP="grupo2"
PASS="shiufi7T"
PAWSERVER="10.16.1.110"
APPWAR="app.war"

mvn clean package
cp webapp/target/webapp.war "${APPWAR}"
chmod +rwx "${APPWAR}"

# scp -o "ProxyCommand ssh ${PAMPERO} -W %h:%p" "${APPWAR}" "${GROUP}:${PASS}@${PAWSERVER}:/app/${APPWAR}"
# sftp -o "ProxyCommand nc -X connect ${PAWSERVER}:22 %h %p" "${APPWAR}" "${PAMPERO}"
echo "If prompted, password for group ${GROUP} is ${PASS}"
sftp -o "ProxyJump ${PAMPERO}:22" "${GROUP}@${PAWSERVER}" <<_EOF
rm web/tmp-${APPWAR}
rm web/${APPWAR}
put ${APPWAR} web/tmp-${APPWAR}
rename web/tmp-${APPWAR} web/${APPWAR}
bye
_EOF

# "${GROUP}@${PAWSERVER}:/app/${APPWAR}"

