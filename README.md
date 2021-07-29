
# Dice Rolling Simulation

Version v1.0.0 

This program rolls dice repeatedly and keep track of how many times each roll (the sum of the upward facing values of the two dice) occurs.

---

# Spring Boot "Microservice"  Dice Rolling Simulation Project

---

REST APIs implemented using JDK 11 / Spring Boot (version 2.5.3) H2 database Maven Project. 


# How to Run   

* Build the project by running `mvn clean package` 
* Building jar in `dice-simulation/target/` folder.
* Once successfully built, run the service by using the following command:
```
java -jar  dice-simulation-v1.0.0-SNAPSHOT.jar
```
# REST API DOCUMENTATION

To reach the full API documantation, please use the link : `http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

# REST APIs Endpoints
Dice roller simulation has 3 endpoints

* POST /v1/rolling-dices : it returns random dice rolling value list based on rolls times. dice-count, dice-side.
* GET /v1/combination-list : returns sum of the rolled dices grouping dice-count and dice-side.
* GET /v1/probability : returns probability dice rolling list grouping by sum of rolling dice side and ratio of rolling times to total rolling times

# SYSTEM OVERVIEW

Statless Restful Api is desing on the system. It means the server does not store any state about the client session on the server side. 
There are some very noticeable advantage for having REST APIs stateless:

* Statelessness helps in scaling the APIs to millions of concurrent users by deploying it to multiple servers. Any server can handle any request because there is no session related dependency.
* Being stateless makes REST APIs less complex – by removing all server-side state synchronization logic.
* A stateless API is also easy to cache as well. Specific software can decide whether or not to cache the result of an HTTP request just by looking at that one request. There’s no nagging uncertainty that state from a previous request might affect the cacheability of this one. It improves the performance of applications.
* The server never loses track of “where” each client is in the application because the client sends all necessary information with each request.

---

We can implement caching on the GET endpoints with using one of the third party caching framework on the web application server.

There are constraints on the POST endpoint query parameters to make the application stable.

The system database is H2. It is a memory-based database therefore if the application is stopped, we lose the data.
NoSQL / RDMS database or column base databases might be used based on the detail requirements. 

The system used native queries for getting optimum performance, and it is set the Isolation level Isolation.REPEATABLE_READ. 
This provides the system access data without reading dirty data.