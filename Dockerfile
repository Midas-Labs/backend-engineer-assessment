FROM gradle:8.7.0-jdk21 AS build
WORKDIR /home/gradle
COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle bootJar --no-daemon

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /home/gradle/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
