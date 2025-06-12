# Etapa 1: Construcción
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=builder /app/target/inventario-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
