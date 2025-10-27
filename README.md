Job Management System
A production-ready Spring Boot REST API for job management with JWT authentication, role-based authorization, and comprehensive testing.

🚀 Features
JWT Authentication & Authorization (USER, EMPLOYER, ADMIN roles)

CRUD Operations with ownership validation

Advanced Search & Filtering

Statistics & Analytics

Input Validation & Global Error Handling

API Documentation (Swagger UI)

Monitoring (Spring Boot Actuator)

Comprehensive Test Suite (11/11 tests passing)

🔐 Authentication
http
POST /api/auth/register
POST /api/auth/login
Required Headers for Protected Endpoints:

http
Authorization: Bearer <your_jwt_token>
📊 API Endpoints
Jobs
GET /api/jobs - List all jobs

GET /api/jobs/{id} - Get job by ID

POST /api/jobs - Create job (EMPLOYER/ADMIN only)

PUT /api/jobs/{id} - Update job (owner or ADMIN)

DELETE /api/jobs/{id} - Delete job (owner or ADMIN)

Search & Filter
GET /api/jobs/search/company?company=TechCorp

GET /api/jobs/search/location?location=Remote

GET /api/jobs/search/title?title=Developer

GET /api/jobs/search/job-type?jobType=FULL_TIME

Analytics
GET /api/jobs/statistics/count - Job counts by type

GET /api/jobs/statistics/company/{company} - Company stats

Admin (ADMIN only)
GET /api/admin/users - List all users

PUT /api/admin/users/{id}/role - Update user role

DELETE /api/admin/users/{id} - Delete user

🛠 Tech Stack
Java 17+ • Spring Boot 3.2 • Spring Security • JWT

PostgreSQL • JPA/Hibernate • Lombok

JUnit 5 • Mockito • H2 (testing)

Maven • Docker Ready

📈 Project Status
✅ Production Ready • ✅ Fully Tested • ✅ Documented • ✅ Secured
