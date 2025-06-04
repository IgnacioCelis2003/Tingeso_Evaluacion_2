FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} tarifa-service.jar
ENTRYPOINT ["java","-jar","/tarifa-service.jar"]