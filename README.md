# Cargopro Backend - Load & Booking Management

## Project Overview

This backend application manages Load and Booking operations for cargo transportation using Spring Boot 3 and PostgreSQL. It provides RESTful APIs supporting CRUD operations, robust status management, validation, pagination, filtering, and global exception handling. The API is fully documented with Swagger/OpenAPI for easy exploration and testing.

---

## Features

- Manage Loads with detailed facility and truck information
- Manage Bookings linked to Loads with transporter and rate details
- Enforced business rules for status transitions:
  - Load status defaults to `POSTED` on creation
  - Load status changes to `BOOKED` when bookings are made
  - Load status changes to `CANCELLED` when bookings are deleted
  - Booking creation is blocked if the Load is `CANCELLED`
  - Booking status transitions (`PENDING`, `ACCEPTED`, `REJECTED`) handled properly
  - Load status reverts to `POSTED` if all bookings are deleted or rejected
- Input validation via Jakarta Bean Validation annotations
- Pagination and filtering support for list APIs
- Global exception handling returning consistent error responses
- Comprehensive unit and integration tests with >60% coverage
- Interactive API documentation with Swagger UI

---

## Technical Stack

- Java 21
- Spring Boot 3.5.x
- Spring Data JPA
- PostgreSQL
- Jakarta Bean Validation
- Lombok (for boilerplate code reduction)
- springdoc-openapi (Swagger UI)
- JUnit 5 & Mockito (unit and integration testing)

---

## Setup Instructions

### 1. Clone the Repository

git clone https://github.com/ramanthatte26/cargopro-backend.git
cd cargopro-backend

text

### 2. Configure PostgreSQL Database

- Create a PostgreSQL database named `cargopro`.
- Make sure PostgreSQL is running locally.
- Update `src/main/resources/application.properties` with the following database connection details:

spring.datasource.url=jdbc:postgresql://localhost:5432/cargopro
spring.datasource.username=postgres
spring.datasource.password=Luffy@26
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

Swagger / OpenAPI config
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

text

### 3. Build and Run the Application

Build the project and start the server:

mvn clean install
mvn spring-boot:run

text

By default, the server listens on `http://localhost:8080`.

### 4. Access API Documentation

Open your browser and visit:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Use this UI to browse, test, and understand all available APIs.

### 5. Run Tests

Run the full test suite with:

mvn test

text

---

## API Usage Overview

### Load Management

- `POST /load`: Create a new Load
- `GET /load`: Get paginated list of Loads with optional filtering by `shipperId`, `truckType`, `status`, `page`, and `size`
- `GET /load/{loadId}`: Retrieve Load details by ID
- `PUT /load/{loadId}`: Update Load information
- `DELETE /load/{loadId}`: Delete a Load

### Booking Management

- `POST /booking`: Create a new Booking for a Load
- `GET /booking`: Get paginated list of Bookings filtered by `loadId`, `transporterId`, `status`, `page`, and `size`
- `GET /booking/{bookingId}`: Retrieve Booking details by ID
- `PUT /booking/{bookingId}`: Update Booking details
- `DELETE /booking/{bookingId}`: Delete a Booking

Refer to the Swagger UI documentation for detailed request/response schemas and example payloads.

---

## Assumptions and Design Decisions

- The default load status is **POSTED** and changes based on booking activities following business rules.
- Input validation is strictly enforced at API entry points to ensure data integrity.
- Pagination is zero-based (`page=0` means the first page) with 10 items per page by default.
- APIs return clear, descriptive error messages for validation failures or not-found resources.
- Data Transfer Objects (DTOs) separate internal data models from API contracts for flexibility.
- Unit and integration tests cover the core logic ensuring reliability and maintainability.
- Swagger annotations provide rich API documentation for ease of use by third-party clients.

---

## Contact

If you have any questions or feedback, please feel free to reach out.

---

Thank you for reviewing my submission!

---

_End of README_
