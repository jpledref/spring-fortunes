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

