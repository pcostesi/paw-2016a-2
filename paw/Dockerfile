FROM maven:3.5.2 AS builder
LABEL maintainer="Pablo Alejandro Costesich <pablo.costesich@gmail.com>"

RUN apt-get install -y ca-certificates-java && update-ca-certificates -f

COPY pom.xml pom.xml
COPY checkstyle.xml checkstyle.xml
COPY webapp webapp
COPY interfaces interfaces
COPY models models
COPY services services
COPY persistence persistence

RUN mvn clean package -DskipTests

FROM jetty:alpine

EXPOSE 8080

COPY --from=builder webapp/target/webapp.war /var/lib/jetty/webapps/grupo2.war
COPY application.properties.docker /.scrumlr.properties
COPY application.properties.docker /var/lib/jetty/application.properties

