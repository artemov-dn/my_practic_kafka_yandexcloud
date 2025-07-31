FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY kafka_spring_consumer/pom.xml .
RUN mvn dependency:go-offline
COPY kafka_spring_consumer/src /app/src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY kafka_spring_producer/kafka-creds /app/kafka-creds
COPY --from=build /app/target/kafka_spring_consumer*.jar  kafka_consumer.jar
ENTRYPOINT ["java","-jar","/kafka_consumer.jar"]