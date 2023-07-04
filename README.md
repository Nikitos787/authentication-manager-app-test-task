# Test Task README

>This app contains the solution for the test task from Inforce, 
> which is a web application with user registration, login, and profile 
> functionalities. The application is built using a web framework and 
> uses a database to store user information. It also implements error handling and validation for user inputs.

## Requirements

The application should have the following functionalities:
1. **User registration**: Allow users to register by providing their name, email address, and password. Perform basic validation on the input fields.
2. **User login**: Implement a login system to authenticate registered users. Use appropriate security measures to store and verify user credentials.
3. **User profile**: Once logged in, users should be able to view and update their profile information, including their name and email address.

## Technologies Used

The test task solution utilizes the following technologies:

- Java 17
- Spring Boot 3.1.0
- Springdoc OpenAPI (Swagger) 2.1.0
- Liquibase
- MySQL 5.7
- Lombok
- Jjwt 0.9.1
- Apache Maven 3.8.7
- REST-assured
- Testcontainers

## Project Structure

The project follows a Three-Tier Architecture with the following layers:

- Presentation layer (Controllers): Accepts requests from clients and sends results back to them.
- Application logic layer (Service): Provides logic to operate on the data sent to and from the DAO and the client.
- Data access layer (Repository): Represents a bridge between the database and the application.

## Getting Started

To get started with the test task solution, follow the instructions below:

### Local Run

* Clone the repository. 
* Set your credentials in `application.properties`. 
* Set your credentials for tests in `application.properties`. 
* Build the project: `mvn clean package`. 
* Run the application.
* Use this postman [collection](https://www.postman.com/supply-observer-16858482/workspace/for-people/collection/27238121-175578a9-c7f0-456b-bea6-4b635bb15ec1?action=share&creator=27238121) or swagger

> You can test the application using Swagger by accessing [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#).

> Additionally, you can use the provided Postman collection for local run or the Docker run. Just import the collection into your Postman and start testing the application.

> Make sure to configure the necessary properties in `application.properties` for tests.

**Good luck with your testing!**
