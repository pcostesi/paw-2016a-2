FROM maven:3.5.2
LABEL maintainer="Pablo Alejandro Costesich <pablo.costesich@gmail.com>"

RUN apt-get install -y ca-certificates-java && update-ca-certificates -f

COPY pom.xml pom.xml
COPY checkstyle.xml checkstyle.xml
COPY webapp webapp
COPY interfaces interfaces
COPY models models
COPY services services
COPY persistence persistence

RUN mvn clean package
