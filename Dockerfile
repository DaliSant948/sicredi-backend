FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/desafio-backend-sicredi-0.0.1-SNAPSHOT.jar desafio_sicredi.jar
ENTRYPOINT ["java","-jar","desafio_sicredi.jar"]
