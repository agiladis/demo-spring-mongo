# Book Store API

This is a CRUD Book Store API built with Java Spring Boot. It allows users to manage books within a bookstore. The application uses MongoDB for data storage and Redis for caching, implementing a caching strategy known as "Caching Aside."

## Tech Stack

- Java Spring Boot
- MongoDB
- Redis

## Features

- **GET /api/books**: Retrieve a list of all books.
- **GET /api/books/{id}**: Retrieve details of a specific book by its ID.
- **POST /api/books**: Add a new book to the bookstore.
- **PUT /api/books/{id}**: Update details of an existing book.
- **DELETE /api/books/{id}**: Delete a book from the bookstore.

## Installation

### Prerequisites

- **Java 17 or above**: Make sure you have JDK 17 or later installed.
- **Maven**: For dependency management and building the project.
- **MongoDB**: A running instance of MongoDB.
- **Redis**: A running instance of Redis.

### Clone the Repository

```bash
git clone https://github.com/agiladis/demo-spring-mongo.git
cd demo-spring-mongo
```

## Configure the Application

Modify the application.properties file located in src/main/resources/ to suit your environment

## Build the Project

```bash
mvn clean install
```

## Run the Application

```bash
mvn spring-boot:run
```