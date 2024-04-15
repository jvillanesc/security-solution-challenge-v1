# Starship App

**Ejecutar sin Docker**

```
> gradle clean build
> java -jar build/libs/starship-app.jar
```

**Ejecutar con Docker**
```
> gradle clean build
> docker build . -t starship-app
> docker run -d -p 8081:8081 starship-app

```