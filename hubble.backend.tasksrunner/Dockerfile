FROM openjdk:8 AS hubble-tasksrunner
ADD . /hubble
WORKDIR /hubble
CMD ["java", "-Dapp.host=mongodb", "-jar", "target/hubble.backend.tasksrunner-1.0-SNAPSHOT.jar"]
