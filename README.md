# Spring-fortunes

Fortunes-like application using Spring. Calling main application's api will randomly print a quote.

## Build application

```bash
mvn package
```

## Run application

```bash
java -jar target/spring.fortunes-0.0.1-SNAPSHOT.jar
```

## Print a quote

```bash
curl http://localhost:8080/
```

## Print a quote without html code

```bash
curl http://localhost:8080/?raw=true
```

## Build Docker image

```bash
docker build -t spring-fortunes -f docker/DockerFile .
```

## Start Docker image on port 8080

```bash
docker run -p 8080:8080 spring-fortunes
```

