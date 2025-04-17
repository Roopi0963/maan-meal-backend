# FROM maven:3.8.4-openjdk-17 AS build

# WORKDIR /app

# COPY pom.xml .
# RUN mvn dependency:go-offline

# COPY src ./src
# RUN mvn clean package -DskipTests

# FROM openjdk:17-jdk-slim

# WORKDIR /app

# COPY --from=build /app/target/artisanPlatform-0.0.1-SNAPSHOT.jar .

# EXPOSE 8080

# ENTRYPOINT ["java", "-jar", "/app/target/artisanPlatform-0.0.1-SNAPSHOT.jar"]
# ----------- Build Stage -----------
    FROM maven:3.8.4-openjdk-17 AS build

    WORKDIR /app
    
    # Copy Maven project files
    COPY pom.xml .
    
    # Download dependencies
    RUN mvn dependency:go-offline
    
    # Copy the source code
    COPY src ./src
    
    # Package the app (skipping tests for speed)
    RUN mvn clean package -DskipTests
    
    
    # ----------- Runtime Stage -----------
    FROM openjdk:17-jdk-slim
    
    WORKDIR /app
    
    # Copy only the built jar from the previous stage
    COPY --from=build /app/target/artisanPlatform-0.0.1-SNAPSHOT.jar app.jar
    
    # Expose the app port
    EXPOSE 8080
    
    # Run the app
    ENTRYPOINT ["java", "-jar", "app.jar"]
    