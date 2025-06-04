FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} rack-service.jar
ENTRYPOINT ["java","-jar","/rack-service.jar"]