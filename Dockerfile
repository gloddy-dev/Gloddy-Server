FROM openjdk:17.0-slim

EXPOSE 8080

ARG PROJECT_DIRECTORY=/build

WORKDIR %PROJECT_DIRECTORY

ENTRYPOINT ["java","-jar","/build/app.jar"]