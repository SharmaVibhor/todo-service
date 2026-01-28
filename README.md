# Todo Service

A backend REST API service for managing simple todo tasks with Spring Boot and JPA.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Building the Project](#building-the-project)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [Docker Support](#docker-support)
- [API Endpoints](#api-endpoints)
- [Database](#database)

## Features

- Create, read, update todo items
- Mark todos as done or not done
- Update todo descriptions
- Automatic past-due detection via scheduled task
- REST API with JSON responses
- In-memory H2 database for easy development
- Comprehensive unit and integration tests

## Technology Stack

- **Java 17**
- **Spring Boot 3.5.10**
  - Spring Web (REST API)
  - Spring Data JPA (Database access)
  - Spring Boot Test (Testing)
- **H2 Database** (In-memory)
- **Maven** (Build tool)
- **JUnit 5** (Testing framework)
- **Mockito** (Mocking framework)
- **Docker** (Containerization)

## Project Structure

```
todo-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/todo_service/
│   │   │       ├── TodoServiceApplication.java      # Main application class
│   │   │       ├── api/                            # REST Controllers & DTOs
│   │   │       │   ├── TodoController.java
│   │   │       │   ├── dto/
│   │   │       │   │   ├── CreateTodoRequest.java
│   │   │       │   │   ├── TodoResponse.java
│   │   │       │   │   └── UpdateDescriptionRequest.java
│   │   │       │   └── error/
│   │   │       │       ├── ApiErrorResponse.java
│   │   │       │       └── GlobalExceptionHandler.java
│   │   │       ├── domain/                         # Domain entities
│   │   │       │   ├── TodoItem.java
│   │   │       │   └── TodoStatus.java
│   │   │       ├── repository/                     # Data access layer
│   │   │       │   └── TodoItemRepository.java
│   │   │       ├── scheduler/                      # Scheduled tasks
│   │   │       │   └── PastDueTodoScheduler.java
│   │   │       └── service/                        # Business logic
│   │   │           ├── TodoService.java
│   │   │           └── exception/
│   │   │               ├── InvalidTodoStateException.java
│   │   │               └── TodoNotFoundException.java
│   │   └── resources/
│   │       └── application.yaml                    # Application configuration
│   └── test/
│       └── java/
│           └── com/example/todo_service/
│               ├── TodoServiceApplicationTests.java
│               ├── TodoServiceTest.java            # Unit tests
│               └── controller/
│                   └── TodoControllerIT.java       # Integration tests
├── DockerFile                                      # Docker configuration
├── pom.xml                                        # Maven configuration
└── README.md                                      # This file
```

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+** (or use the included Maven wrapper)
- **Docker** (optional, for containerization)

### Clone the Repository

```bash
git clone https://github.com/SharmaVibhor/todo-service.git
cd todo-service
```

## Building the Project

### Using Maven Wrapper (Recommended)

**Windows:**

```cmd
.\mvnw.cmd clean install
```

**Linux/Mac:**

```bash
./mvnw clean install
```

### Using System Maven

```bash
mvn clean install
```

This will:

- Compile the source code
- Run all tests
- Package the application as a JAR file in `target/`

## Running the Application

### Option 1: Using Maven

**Windows:**

```cmd
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**

```bash
./mvnw spring-boot:run
```

### Option 2: Using JAR File

```bash
java -jar target/todo-service-0.0.1-SNAPSHOT.jar
```

The application will start on **http://localhost:8080**

### Verify Application is Running

```bash
curl http://localhost:8080/todos
```

## Running Tests

### Run All Tests

**Windows:**

```cmd
.\mvnw.cmd test
```

**Linux/Mac:**

```bash
./mvnw test
```

### Run Specific Test Class

```bash
.\mvnw.cmd test -Dtest=TodoServiceTest
```

### Test Coverage

The project includes:

- **Unit Tests**: Testing service layer logic with mocked dependencies
- **Integration Tests**: Testing REST API endpoints with full Spring context
- **Total Tests**: 9 tests (all passing ✓)

## Docker Support

### Build Docker Image

```bash
docker build -t todo-service:latest .
```

The Dockerfile uses a multi-stage build:

1. **Build stage**: Compiles and packages the application using Maven
2. **Runtime stage**: Creates a lightweight image with only the JRE and application JAR

### Run Docker Container

```bash
docker run -p 8080:8080 todo-service:latest
```

## API Endpoints

### Base URL

```
http://localhost:8080
```

### Endpoints

| Method  | Endpoint                  | Description        | Request Body                                | Response                   |
| ------- | ------------------------- | ------------------ | ------------------------------------------- | -------------------------- |
| `POST`  | `/todos`                  | Create a new todo  | `CreateTodoRequest`                         | `TodoResponse` (201)       |
| `GET`   | `/todos`                  | Get all todos      | Query param: `includeDone` (default: false) | `List<TodoResponse>` (200) |
| `GET`   | `/todos/{id}`             | Get todo by ID     | -                                           | `TodoResponse` (200)       |
| `PATCH` | `/todos/{id}/description` | Update description | `UpdateDescriptionRequest`                  | No content (204)           |
| `PATCH` | `/todos/{id}/done`        | Mark as done       | -                                           | No content (204)           |
| `PATCH` | `/todos/{id}/not-done`    | Mark as not done   | -                                           | No content (204)           |

### Request/Response Examples

#### Create Todo

```bash
curl -X POST http://localhost:8080/todos \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Complete project documentation",
    "dueAt": "2026-02-01T10:00:00Z"
  }'
```

Response (201 Created):

```json
{
  "id": 1,
  "description": "Complete project documentation",
  "status": "NOT_DONE",
  "createdAt": "2026-01-28T19:00:00Z",
  "dueAt": "2026-02-01T10:00:00Z",
  "doneAt": null
}
```

#### Get All Todos

```bash
curl http://localhost:8080/todos
```

Response (200 OK):

```json
[
  {
    "id": 1,
    "description": "Complete project documentation",
    "status": "NOT_DONE",
    "createdAt": "2026-01-28T19:00:00Z",
    "dueAt": "2026-02-01T10:00:00Z",
    "doneAt": null
  }
]
```

#### Mark Todo as Done

```bash
curl -X PATCH http://localhost:8080/todos/1/done
```

Response: 204 No Content

#### Update Description

```bash
curl -X PATCH http://localhost:8080/todos/1/description \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Updated documentation task"
  }'
```

Response: 204 No Content

### Error Responses

#### Todo Not Found (404)

```json
{
  "timestamp": "2026-01-28T19:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Todo with ID 999 not found",
  "path": "/todos/999"
}
```

#### Invalid State (400)

```json
{
  "timestamp": "2026-01-28T19:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Past-due items cannot be modified",
  "path": "/todos/1/done"
}
```

## Database

### H2 Console

The H2 console is enabled for development and can be accessed at:

```
http://localhost:8080/h2-console
```

**Connection Details:**

- **JDBC URL**: `jdbc:h2:mem:todo-db`
- **Username**: `sa`
- **Password**: _(empty)_

### Database Schema

**Table: `todo_items`**

| Column        | Type         | Constraints                               |
| ------------- | ------------ | ----------------------------------------- |
| `id`          | BIGINT       | PRIMARY KEY, AUTO_INCREMENT               |
| `description` | VARCHAR(255) | NOT NULL                                  |
| `status`      | ENUM         | NOT NULL (`DONE`, `NOT_DONE`, `PAST_DUE`) |
| `created_at`  | TIMESTAMP    | NOT NULL                                  |
| `due_at`      | TIMESTAMP    | NOT NULL                                  |
| `done_at`     | TIMESTAMP    | NULLABLE                                  |

### Scheduled Tasks

The application includes a scheduler that runs every 5 minutes to automatically mark overdue todos as `PAST_DUE`. See `PastDueTodoScheduler.java`.
