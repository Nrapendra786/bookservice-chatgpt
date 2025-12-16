# 📚 bookstore-service

`bookstore-service` is a Spring Boot 3 REST API for managing books and customers in a simple bookstore domain.  
It uses **PostgreSQL** as the database, **Flyway** for database migrations, and **Docker** for containerized deployment.  
The project also includes **integration tests** using `MockMvc`.

---

## ✨ Features

- ✅ CRUD operations for **Books**
- ✅ CRUD operations for **Customers**
- ✅ Basic request **validation** (e.g. required fields, formats)
- ✅ **Integration tests** with `MockMvc` for REST endpoints

---

## 🛠 Tech Stack

- **Backend:** Spring Boot 3 (Spring Web, Spring Data JPA, Validation)
- **Database:** PostgreSQL
- **Migrations:** Flyway
- **Build Tool:** Maven 
- **Testing:** JUnit, Spring Boot Test, MockMvc
- **Containerization:** Docker

---

## 📦 Getting Started

### 1. Prerequisites

- Java 17+ (required by Spring Boot 3)
- Maven 
- Docker & Docker Compose
- PostgreSQL (if not using Docker for DB)

---

### 2. Configuration

By default, the application expects a PostgreSQL database. Typical `application.properties` settings:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bookstore
    username: bookstore_user
    password: bookstore_pass
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
  flyway:
    enabled: true
    locations: classpath:db/migration
    
./mvnw clean package
# or
mvn clean package

docker build -t bookstore-service:latest .

#To start all docker containers
docker compose up

# Curl Example
http://localhost:8080

#To Run the Application from cmd
./mvnw spring-boot:run
# or
mvn spring-boot:run

#To Run the Test
./mvnw test
# or
mvn test

#Project Structure

bookstore-service
├─ src
│  ├─ main
│  │  ├─ java/com/example/bookstore
│  │  │  ├─ controller   # REST controllers for books & customers
│  │  │  ├─ service      # Business logic
│  │  │  ├─ repository   # Spring Data JPA repositories
│  │  │  └─ model        # JPA entities & DTOs
│  │  └─ resources
│  │     ├─ application.yml
│  │     └─ db/migration  # Flyway migration scripts
│  └─ test
│     └─ java/com/example/bookstore
│        └─ controller    # MockMvc integration tests
└─ Dockerfile
└─ docker-compose.yml


# bookservice-chatgpt
