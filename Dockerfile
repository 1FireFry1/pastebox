FROM openjdk:11-jdk as build
MAINTAINER SAVELYEV IGOR
COPY target/pastebox-0.0.1-SNAPSHOT.jar pastebox.jar
ENTRYPOINT ["java","-jar","/pastebox.jar"]