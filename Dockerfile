# 1. Build the JAR using Maven
FROM maven:3.6.3-openjdk-17 AS builder
WORKDIR /app

# 2. Copy Maven project files
COPY pom.xml .
COPY src ./src

# 3. Build the JAR
RUN mvn clean package -DskipTests

# 4. Run the app
FROM eclipse-temurin:17-jdk

# 5. Set working directory
WORKDIR /app

# 6. Copy JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# 7. Expose port 8080
EXPOSE 8080

# 8. Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]