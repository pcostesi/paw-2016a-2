FROM jetty:alpine

EXPOSE 8080

COPY webapp/target/webapp.war /var/lib/jetty/webapps/grupo2.war
COPY application.properties.docker /var/lib/jetty/application.properties
