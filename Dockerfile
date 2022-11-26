FROM openjdk:11

EXPOSE 8080

ADD target/synpulse-backend.jar synpulse-backend.jar

ENTRYPOINT ["java", "-jar", "synpulse-backend.jar"]
