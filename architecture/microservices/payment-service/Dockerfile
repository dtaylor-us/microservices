# ---- Build Stage ----
FROM eclipse-temurin:21 AS build
WORKDIR /app

COPY . .

# Install Maven (required)
RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests -pl payment-service -am

# ---- Run Stage ----
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/payment-service/target/payment-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
