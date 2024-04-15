FROM openjdk:17-alpine
WORKDIR /home
COPY /build/libs/movement-app.jar movement-app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "movement-app.jar"]
