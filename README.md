## Point System

The project calculates reward points based on transaction amount. 

## Original Assignment 

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction.

(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

Solve using Spring Boot
Create a RESTful endpoint
Make up a data set to best demonstrate your solution
Check solution into GitHub

## Modifications to the Project

I originally planned on adding a persistence layer to the project, hence the `id` property, so we can GET the points each customer has accumulated, but for times sake I didn't implement it. 

## Assumptions

Transactions can have cents. For simplicitie’s sake, I just dropped the change. That can easily be accounted for later. 

## Project Requirmenets

- Java 17
- Gradle

## Configuration

I made the reward system configurable for sales purposes, promotions, etc.

## Build & Run

Run `./gradlew clean build` to build the project and `./gradlew bootRun` to run. The postman collection is in the project root. 

## Final Thoughts

- I dont like `@SpringBootTests` integration tests in the controller- The controller is already responsible for so much. Not included in this project is custom serialization/deserialization, which we definitely want to write tests for if they exist. Theres also controller advise...Why add integration tests to the mix? Ideally, they would be Automated Postman Tests, or included in another testing file. 
- Spring Boot is designed for the microservice architecture. Our services are supposed to be small. Why create interfaces between classes when we only have 1 implementation 95% of the time? Use them when we need them, dont when we dont. Mockito allows us to still test everything as a unit, independently, without dependencies, without interfaces. 
- I like using BDD when I can, and since Spring Boot is stateless, the test naming convention `whenSomethingHappens_thenExpectThisResult` is clean and easy to read.
- If the point calculator feature develops over time, (for example, by increasing the number of variables,) we could utilize "parameterized testing".
- Having high test coverage as a principle is a good goal to have, but when test converage starts getting into the high 80's and into the 90's, tests dont add as much value. They are expensive, hard to maintain, and sometimes we get into the terrain of writing tests for the sake of test coverage, and not writing tests for the true purpose of writing test, which we all know, including creating proper guardrails for code, helps make it small and readable, defining clear expectations of each method and class, etc. 
- As consultants we strive to educate our clients on aiming for the best practical investment of time and money for their software product, but we should also work within their expected organizational expectations and constraints. 
