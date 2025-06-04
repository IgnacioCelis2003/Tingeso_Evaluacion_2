FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} reporte-service.jar
ENTRYPOINT ["java","-jar","/reporte-service.jar"]