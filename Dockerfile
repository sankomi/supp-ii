FROM maven:eclipse-temurin AS build
WORKDIR /app
COPY pom.xml .
RUN mvn verify --fail-never
COPY src ./src
COPY config ./config
RUN mkdir db
RUN mvn package

FROM ubuntu:lunar
RUN apt-get update && apt-get install -y openjdk-17-jre
WORKDIR /app
COPY --from=build /app/target/supp-ii-0.0.1-SNAPSHOT.jar ./supp-ii.jar
COPY config ./config
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "supp-ii.jar"]
