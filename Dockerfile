
FROM eclipse-temurin:17


RUN apt-get update && apt-get install -y maven


WORKDIR /backend


COPY . .


CMD mvn spring-boot:run
