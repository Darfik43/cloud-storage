FROM gradle:8.8.0-jdk17 AS Build
WORKDIR /
COPY build.gradle .
COPY /src /src
RUN gradle build -x test

FROM openjdk:17-oracle

COPY --from=build /build/libs/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]