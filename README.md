
# Dice Rolling Simulation

Version v1.0.0 

This program rolls dice repeatedly and keep track of how many times each roll (the sum of the upward facing values of the two dice) occurs.

---

# Spring Boot "Microservice"  Dice Rolling Simulation Project

---

REST APIs implemented using JDK 11 / Spring Boot (version 2.5.3) H2 database Maven Project. 


# How to Run   

* Build the project by running `mvn clean package` 
* Building jar in `dice-distribution-simulation/target/` folder.
* Once successfully built, run the service by using the following command:
```
java -jar  dice-distribution-simulation-v1.0.0-SNAPSHOT.jar
```
# Creating a Docker Image
The dice distribution simulation could be run within a container in a Kubernetes system. 
To produce this container a docker image of dice distribution simulation machine must be created.

` docker build -t dice-distribution-simulation:lates .`
This will create a docker image with a tag on the local docker machine. We can push the image 
an artifactory with using `docker push` command.

# REST API DOCUMENTATION

To reach the full API documantation, please use the link : `http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

# REST APIs Endpoints
Dice roller simulation has 3 endpoints

* POST /v1/rolling-dices : it returns random dice rolling value list based on rolls times. dice-count, dice-side.
* GET /v1/combination-list : returns sum of the rolled dices grouping dice-count and dice-side.
* GET /v1/probability : returns probability dice rolling list grouping by sum of rolling dice side and ratio of rolling times to total rolling times

# SYSTEM OVERVIEW

MVC design pattern and stateless beans are used on the project. It means the server does not store any state about the client session on the server side. 
There are some very noticeable advantage for having REST APIs stateless:

* Statelessness helps in scaling the APIs to millions of concurrent users by deploying it to multiple servers. Any server can handle any request because there is no session related dependency.
* Being stateless makes REST APIs less complex – by removing all server-side state synchronization logic.
* A stateless API is also easy to cache as well. Specific software can decide whether or not to cache the result of an HTTP request just by looking at that one request. There’s no nagging uncertainty that state from a previous request might affect the cacheability of this one. It improves the performance of applications.
* The server never loses track of “where” each client is in the application because the client sends all necessary information with each request.

---

We can implement caching on the GET endpoints with using one of the third party caching framework on the web application server.

There are constraints on the POST endpoint query parameters in order to make the application stable.

The system database is H2. It is a memory-based database therefore if the application is stopped, we lose the data.
There are different type of modern database such as NoSQL, RDMS database or column base databases. We can choose one of them based on data insertion and update frequency, ACID compliance and analytical/transactional app. 

Each of simulation records entered H2 database transactional and each read from H2 database Isolation level is Isolation.REPEATABLE_READ. 
This setup provides basically free from dirty reading data from H2 environment on concurrent reading.

Spring framework Global exception handling is used on the solution. The advantage of global exception handling is all the code necessary for handling any kind of error in the application.

Spring validation annotations are used in order to checks and validate user inputs.