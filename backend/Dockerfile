FROM gradle:jdk21 AS build
WORKDIR /app

# Copy Gradle wrapper and setting files first to leverage Gradle caching
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Download dependencies (this step is cached if no changes in dependencies)
RUN gradle build --no-daemon || return 0

# Copy the rest of the project files
COPY . .

# Build the application
RUN gradle bootJar --no-daemon

FROM ubuntu:22.04

RUN apt-get update && apt-get install -y \
  openjdk-21-jre-headless \
  && rm -rf /var/lib/apt/lists/*
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
RUN java -jar /app/app.jar --install-deps && rm -rf /var/lib/apt/lists/*
RUN java -jar /app/app.jar --install-browser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]