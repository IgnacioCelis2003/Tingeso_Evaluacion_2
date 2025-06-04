FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} grupo-descuento-service.jar
ENTRYPOINT ["java","-jar","/grupo-descuento-service.jar"]