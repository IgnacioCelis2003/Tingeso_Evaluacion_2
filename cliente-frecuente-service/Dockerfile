FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} cliente-frecuente-service.jar
ENTRYPOINT ["java","-jar","/cliente-frecuente-service.jar"]