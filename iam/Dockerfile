FROM openjdk:8u191-jre-alpine3.8
ADD build/libs/iam-0.0.1-SNAPSHOT.jar iam.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "iam.jar"]
