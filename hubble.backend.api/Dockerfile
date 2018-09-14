FROM openjdk:8 AS hubble-api
ADD . /hubble
WORKDIR /hubble
CMD ["java", "-Dapp.host=hubble-mongodb", "-jar", "target/hubble.backend.api-0.0.1-SNAPSHOT.jar"]
