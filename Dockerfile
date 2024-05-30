# Use a base image with JDK
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file to the container
COPY target/clientMicroservice-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 9092

# Command to run the executable jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
