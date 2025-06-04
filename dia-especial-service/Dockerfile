FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} dia-especial-service.jar
ENTRYPOINT ["java","-jar","/dia-especial-service.jar"]