FROM openjdk:17-jdk
WORKDIR /app
COPY app.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]