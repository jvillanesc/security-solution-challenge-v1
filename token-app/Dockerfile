FROM openjdk:17-alpine
WORKDIR /home
COPY /build/libs/token-app.jar token-app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "token-app.jar"]
