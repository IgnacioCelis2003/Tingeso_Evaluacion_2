FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} reserva-service.jar
ENTRYPOINT ["java","-jar","/reserva-service.jar"]