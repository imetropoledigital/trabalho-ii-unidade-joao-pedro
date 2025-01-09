FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

#copiar arquivo gerando após ./mvnw clean package
COPY target/bdnosql-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]