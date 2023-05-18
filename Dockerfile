FROM maven:3.8.3-jdk-11-slim AS build

RUN mkdir -p /project

COPY . /project

WORKDIR /project

RUN mvn clean package -DskipTests

FROM openjdk:11

RUN mkdir /app

WORKDIR /app

COPY --from=build /project/target/transaction-monitor.jar /app/transaction-monitor.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "transaction-monitor.jar"]
