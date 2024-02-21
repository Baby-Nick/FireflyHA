FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:17-jdk-alpine

ENV NUM_WORKERS=10 \
#     URL_FILEPATH=endg-urls \
    URL_FILEPATH=test-urls.txt \
    WORDS_BANK_FILEPATH=words-bank.txt


WORKDIR /app

COPY --from=build /app/target/homeAssigment-1.0-jar-with-dependencies.jar .
COPY src/test-urls.txt .
COPY src/words-bank.txt .
COPY src/endg-urls .

CMD ["java", "-jar", "homeAssigment-1.0-jar-with-dependencies.jar"]
