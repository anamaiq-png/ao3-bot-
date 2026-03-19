FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

COPY . .

RUN mvn clean install

CMD ["java", "-cp", "target/ao3-discord-1.0.jar", "Main"]
