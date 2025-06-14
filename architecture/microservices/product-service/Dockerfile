# ---- Build Stage ----
FROM eclipse-temurin:21 AS build
WORKDIR /app

# Copy the entire multi-module context (not just order-service)
COPY . .

# Install Maven
RUN apt-get update && apt-get install -y maven


RUN mvn clean package -DskipTests -pl order-service -am

# ---- Run Stage ----
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/product-service/target/product-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
