FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app
RUN ./gradlew clean build --no-daemon
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/server.v1-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]