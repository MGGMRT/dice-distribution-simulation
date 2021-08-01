FROM adoptopenjdk/openjdk11:alpine-jre

ADD ./target/dice-distribution-simulation-*.jar /app/dice-distribution-simulation.jar

RUN /app/dice-distribution-simulation.jar

WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/dice-distribution-simulation.jar"]