# 🚛 Cargopro Backend – Load & Booking Management

## 📘 Project Overview

This backend application manages **Load and Booking operations** for cargo transportation using **Spring Boot 3** and **PostgreSQL**. It exposes **RESTful APIs** for CRUD operations, enforces business rules, supports pagination/filtering, and provides interactive API documentation with **Swagger/OpenAPI**.

---

## ✨ Features

- 📦 **Load Management**

  - Detailed facility and truck information
  - Default status: `POSTED`
  - Auto-transition to:
    - `BOOKED` when bookings are created
    - `CANCELLED` when bookings are deleted
    - `POSTED` if all bookings are deleted or rejected

- 📝 **Booking Management**

  - Linked to loads with transporter and rate details
  - Status transitions: `PENDING`, `ACCEPTED`, `REJECTED`
  - Booking creation is blocked if the Load is `CANCELLED`

- ✅ **Validation & Consistency**

  - Input validation using **Jakarta Bean Validation**
  - Global exception handling for consistent error responses

- 📄 **API Design**

  - Pagination and filtering support
  - Full Swagger UI documentation

- 🧪 **Testing**
  - > 60% test coverage
  - Unit and integration tests with **JUnit 5** and **Mockito**

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.5.x
- Spring Data JPA
- PostgreSQL
- Jakarta Bean Validation
- Lombok
- springdoc-openapi (Swagger)
- JUnit 5 & Mockito

---

## 🚀 Setup Instructions

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/ramanthatte26/cargopro-backend.git
cd cargopro-backend
```

---

### 2️⃣ Configure PostgreSQL

Create a database named `cargopro` in your local PostgreSQL instance.

Then update the `application.properties` file:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/cargopro
spring.datasource.username=postgres
spring.datasource.password=Luffy@26

# JPA Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

---

### 3️⃣ Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

App will run at: `http://localhost:8080`

---

### 4️⃣ Access API Documentation

Visit:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Explore and test APIs using the built-in Swagger UI.

---

### 5️⃣ Run Tests

```bash
mvn test
```

---

## 📚 API Endpoints Overview

### 🚚 Load Management

| Method | Endpoint         | Description                      |
| ------ | ---------------- | -------------------------------- |
| POST   | `/load`          | Create a new Load                |
| GET    | `/load`          | List Loads with optional filters |
| GET    | `/load/{loadId}` | Get Load details by ID           |
| PUT    | `/load/{loadId}` | Update Load details              |
| DELETE | `/load/{loadId}` | Delete a Load                    |

**Optional Filters**: `shipperId`, `truckType`, `status`, `page`, `size`

---

### 📦 Booking Management

| Method | Endpoint               | Description                     |
| ------ | ---------------------- | ------------------------------- |
| POST   | `/booking`             | Create a new Booking for a Load |
| GET    | `/booking`             | List Bookings with filters      |
| GET    | `/booking/{bookingId}` | Get Booking details by ID       |
| PUT    | `/booking/{bookingId}` | Update Booking details          |
| DELETE | `/booking/{bookingId}` | Delete a Booking                |

**Optional Filters**: `loadId`, `transporterId`, `status`, `page`, `size`

For request/response details and examples, refer to the [Swagger UI](http://localhost:8080/swagger-ui.html).

---

## 💡 Design Decisions & Assumptions

- Load status changes automatically based on booking activities.
- Strict validation to enforce input integrity.
- Zero-based pagination (i.e., `page=0` is the first page).
- APIs return clear, user-friendly error messages.
- DTOs separate API contracts from internal entities.
- All core logic is covered with unit & integration tests.
- Swagger annotations enhance API discoverability.

---

## 📬 Contact

For feedback or queries, feel free to reach out via GitHub or open an issue.

---

Thank you for checking out **Cargopro Backend**!

---

_End of README_ ✅
