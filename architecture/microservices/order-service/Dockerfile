# ---- Build Stage ----
FROM eclipse-temurin:21 AS build
WORKDIR /build

COPY . .

# Install Maven
RUN apt-get update && apt-get install -y maven


RUN mvn clean package -DskipTests -pl order-service -am

# ---- Run Stage ----
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /build/order-service/target/order-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
