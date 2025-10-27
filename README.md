# 💼 Job Management System

A **production-ready Spring Boot REST API** for job management with **JWT authentication**, **role-based authorization**, and **comprehensive testing**.

---

## 🚀 Features

* ✅ JWT Authentication & Authorization (`USER`, `EMPLOYER`, `ADMIN` roles)
* ✅ CRUD Operations with ownership validation
* ✅ Advanced Search & Filtering
* ✅ Statistics & Analytics endpoints
* ✅ Input Validation & Global Error Handling
* ✅ API Documentation (Swagger UI)
* ✅ Monitoring via Spring Boot Actuator
* ✅ Comprehensive Test Suite (**11/11 tests passing**)

---

## 🔐 Authentication

**Endpoints**

```
POST /api/auth/register
POST /api/auth/login
```

**Required Header for Protected Endpoints:**

```
Authorization: Bearer <your_jwt_token>
```

---

## 📊 API Endpoints

### 🧾 Jobs

```
GET    /api/jobs                 → List all jobs  
GET    /api/jobs/{id}            → Get job by ID  
POST   /api/jobs                 → Create job (EMPLOYER/ADMIN only)  
PUT    /api/jobs/{id}            → Update job (owner or ADMIN)  
DELETE /api/jobs/{id}            → Delete job (owner or ADMIN)
```

### 🔍 Search & Filter

```
GET /api/jobs/search/company?company=TechCorp  
GET /api/jobs/search/location?location=Remote  
GET /api/jobs/search/title?title=Developer  
GET /api/jobs/search/job-type?jobType=FULL_TIME
```

### 📈 Analytics

```
GET /api/jobs/statistics/count                 → Job counts by type  
GET /api/jobs/statistics/company/{company}     → Company-specific stats
```

### 👑 Admin (ADMIN only)

```
GET    /api/admin/users              → List all users  
PUT    /api/admin/users/{id}/role    → Update user role  
DELETE /api/admin/users/{id}         → Delete user
```

---

## 🛠 Tech Stack

| Category             | Technologies         |
| -------------------- | -------------------- |
| **Language**         | Java 17+             |
| **Framework**        | Spring Boot 3.2      |
| **Security**         | Spring Security, JWT |
| **Database**         | PostgreSQL           |
| **ORM**              | JPA / Hibernate      |
| **Utilities**        | Lombok               |
| **Testing**          | JUnit 5, Mockito, H2 |
| **Build Tool**       | Maven                |
| **Containerization** | Docker Ready         |

---

## ⚙️ How to Run Locally

```bash
# Clone the repository
git clone https://github.com/your-username/job-management-system.git
cd job-management-system

# Build the project
mvn clean install

# Run the app
mvn spring-boot:run
```

Access the API at:

```
http://localhost:8080
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧩 Docker Setup

```bash
docker build -t job-management-system .
docker run -p 8080:8080 job-management-system
```

---

## 📈 Project Status

✅ **Production Ready**
✅ **Fully Tested**
✅ **Documented**
✅ **Secured**

