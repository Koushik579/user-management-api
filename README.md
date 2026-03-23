# 🚀 User Management API – Spring Boot

## 📖 Overview

User Management API is a Spring Boot-based RESTful application that provides full CRUD operations for managing users.

The project follows a clean layered architecture and incorporates modern backend practices such as:

- DTO (Data Transfer Object) pattern
- Input validation
- Global exception handling

This makes the application closer to real-world production-grade APIs.

---

## ✨ Features

- ✅ CRUD operations (Create, Read, Update, Delete)
- 🌐 RESTful API design
- 🧱 Layered architecture:
  - Controller → Service → Repository → Entity
- 📦 DTO pattern for request & response handling
- 🛡️ Input validation using annotations (`@Valid`)
- ⚠️ Global exception handling using `@ControllerAdvice`
- 🧾 Custom error response structure
- 🗄️ Database integration using Spring Data JPA
- 🧹 Clean and maintainable codebase

---

## 🛠 Tech Stack

| Category     | Technology           |
|--------------|---------------------|
| Language     | Java                |
| Framework    | Spring Boot         |
| ORM          | Spring Data JPA     |
| Build Tool   | Maven               |
| Database     | PostgreSQL          |

---

## 📂 Project Structure

```
src/main/java/com/koushik/usermanagement
│
├── controller/ # Handles HTTP requests
│ └── UserController.java
│
├── service/ # Business logic layer
│ └── UserService.java
│
├── repository/ # Data access layer (JPA)
│ └── UserRepository.java
│
├── entity/ # Database entity
│ └── User.java
│
├── dto/ # Data Transfer Objects
│ ├── UserRequestDTO.java
│ └── UserResponseDTO.java
│
├── exception/ # Global exception handling
│ ├── ErrorResponse.java
│ └── GlobalExceptionHandler.java
│
└── UserManagementApiApplication.java

```

---

## ⚙️ How to Run

### 1️⃣ Clone the Repository

```
git clone <your-repo-url>
cd user-management-api
2️⃣ Configure Database

Update the file:

src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:your_database_url
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
3️⃣ Run the Application
./mvnw spring-boot:run

On Windows:

mvnw.cmd spring-boot:run
🌐 API Base URL
http://localhost:8080
📌 API Endpoints
Method	Endpoint	Description
GET	/users	Get all users
GET	/users/{id}	Get user by ID
POST	/users	Create new user
PUT	/users/{id}	Update user
DELETE	/users/{id}	Delete user
🔄 Data Flow
Client → DTO → Controller → Service → Repository → Database
                                     ↓
Client ← DTO ← Controller ← Service ← Entity
🛡️ Validation & Exception Handling
🔹 Validation
Implemented using annotations such as:
@NotNull
@NotBlank
@Email
@Size
Applied on DTO classes
Ensures only valid data enters the system
🔹 Exception Handling
Centralized using @ControllerAdvice
Custom error responses via ErrorResponse
Handles:
Validation errors
Resource not found exceptions
General runtime exceptions
📄 Example Error Response

{
    "timeStamp": "2026-03-23T20:32:42.2692842",
    "path": "/user/19",
    "status": 404,
    "message": "Cannot find the user with id: 19",
    "error": null
}

{
    "timeStamp": "2026-03-23T20:34:36.0097216",
    "path": "/user/2",
    "status": 400,
    "message": "Validation Error",
    "error": {
        "name": "Name cannot be empty"
    }
}
```
## 📈 Current Status
✔ CRUD operations implemented
✔ JPA integration
✔ DTO layer implemented
✔ Input validation added
✔ Global exception handling implemented
✔ Clean layered architecture


## 🔮 Future Improvements

📄 API documentation (Swagger / OpenAPI)
🔐 Authentication & Authorization (Spring Security + JWT)
📊 Pagination & Sorting
🧪 Unit & Integration Testing
📜 Logging & Monitoring


## 🎯 Learning Objectives
Build scalable REST APIs using Spring Boot
Implement DTO pattern correctly
Apply validation and exception handling
Understand layered architecture
Follow backend best practices used in real-world projects


## 👤 Author

Koushik Karmakar
Backend Developer (Java | Spring Boot | SQL)

## 📌 Notes

This project is part of my backend development journey.
The codebase will continue to evolve with new features and improvements following industry best practices.