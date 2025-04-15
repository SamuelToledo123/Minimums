# Använd en officiell JDK som base image
FROM openjdk:20-jdk

# Skapa en katalog i containern
WORKDIR /app

# Kopiera det byggda JAR-filen till containern
COPY target/*.jar app.jar

# Starta appen när containern körs
ENTRYPOINT ["java", "-jar", "app.jar"]