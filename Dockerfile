FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER heroku.com
COPY target/jwt-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]