# Stage 1: Build the application
FROM adoptopenjdk:11-jdk-hotspot AS build
WORKDIR /app
COPY . .

# Install Java 21
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://download.java.net/java/GA/jdk17/0d483333a00540d886896bac774ff48b/35/GPL/openjdk-17_linux-x64_bin.tar.gz && \
    tar -xzvf openjdk-17_linux-x64_bin.tar.gz && \
    mv jdk-17 /opt && \
    rm openjdk-17_linux-x64_bin.tar.gz

# Set Java 21 as default
ENV JAVA_HOME /opt/jdk-17
ENV PATH $JAVA_HOME/bin:$PATH

RUN ./gradlew build

# Stage 2: Create a runtime image
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/application.jar
CMD ["java", "-jar", "application.jar"]