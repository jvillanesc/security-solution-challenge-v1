FROM openjdk:17-alpine
WORKDIR /home
COPY /build/libs/deposit-app.jar deposit-app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "deposit-app.jar"]
