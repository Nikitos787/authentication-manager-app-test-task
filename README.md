# Authentication-manager-app

>This app contains the solution for the test task from Inforce, 
> which is a web application with user registration, login, and profile 
> functionalities. The application is built using a web framework and 
> uses a database to store user information. It also implements error handling and validation for user inputs.

## :mag: Requirements

The application should have the following functionalities:
1. **User registration**: Allow users to register by providing their name, email address, and password. Perform basic validation on the input fields.
2. **User login**: Implement a login system to authenticate registered users. Use appropriate security measures to store and verify user credentials.
3. **User profile**: Once logged in, users should be able to view and update their profile information, including their name and email address.

## :hammer: Technologies Used

The test task solution utilizes the following technologies:

- Java 17
- Spring Boot 3.1.0
- Springdoc OpenAPI (Swagger) 2.1.0
- Liquibase
- MySQL 5.7
- Lombok
- Jjwt 0.9.1
- Apache Maven 3.8.7
- MapStruct
- REST-assured
- Testcontainers

## [VIDEO PRESENTATION](https://www.youtube.com/watch?v=3PtqCYZRc84)

## :green_book: Project Structure

> The project has a Three-Tier Architecture:

| Layer                                 | Responsibilities                                                              | 
|---------------------------------------|-------------------------------------------------------------------------------|
| **Presentation layer (Controllers)**  | Accepts requests from clients and sends results back to them.                 |
| **Application logic layer (Service)** | Provide logic to operate on the data sent to and from the DAO and the client. |
| **Data access layer (Repository)**    | Represents a bridge between the database and the application.                 |

## Getting Started

To get started with the test task solution, follow the instructions below:

### Local Run

* Clone the repository. 
* Set your credentials in `application.properties`. 
* Set your credentials for tests in `application.properties`. 
* Build the project: `mvn clean package`. 
* Run the application.
* Use this postman [collection](https://www.postman.com/supply-observer-16858482/workspace/for-people/collection/27238121-175578a9-c7f0-456b-bea6-4b635bb15ec1?action=share&creator=27238121) or swagger

### Run with docker

* Clone the repository.
* run with command `docker-compose up`
* Use this postman [collection](https://www.postman.com/supply-observer-16858482/workspace/for-people/collection/27238121-060e893c-17f3-4ab6-a40b-ed3f1060ed92?action=share&creator=27238121)

> You can test the application using Swagger by accessing [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#).

> Additionally, you can use the provided Postman collection for local run or the Docker run. Just import the collection into your Postman and start testing the application.

> Make sure to configure the necessary properties in `application.properties` for tests.

> User can register/login/update own info. But user can find user by id or params or with sorting by field with pagination and counting. Also, Manager can update role in user

**Good luck with your testing!**
