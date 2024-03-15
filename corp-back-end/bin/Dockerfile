FROM openjdk:17-alpine3.14

WORKDIR /app

COPY target/corp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
