# Build Stage
FROM openjdk:17-jdk-slim AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

# Runtime Stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/springboot-crud-0.0.1-SNAPSHOT.jar /app/springboot-crud.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "springboot-crud.jar"]
