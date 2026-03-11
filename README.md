# 🏥 MedSync — Healthcare Management System

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?style=for-the-badge&logo=springboot)
![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![JWT](https://img.shields.io/badge/JWT-Security-red?style=for-the-badge&logo=jsonwebtokens)
![Swagger](https://img.shields.io/badge/Swagger-UI-85EA2D?style=for-the-badge&logo=swagger)

> A full-featured **Healthcare Management REST API** built with Spring Boot — enabling patients to book appointments online, doctors to manage their schedules, and admins to oversee the entire system securely.

---

## 📌 Table of Contents

- [About The Project](#-about-the-project)
- [Real World Problem Solved](#-real-world-problem-solved)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Architecture](#-project-architecture)
- [API Endpoints](#-api-endpoints)
- [Getting Started](#-getting-started)
- [Database Schema](#-database-schema)
- [Security Flow](#-security-flow)
- [Challenges Solved](#-challenges-solved)
- [Author](#-author)

---

## 🎯 About The Project

**MedSync** is a backend REST API system for managing healthcare operations in clinics and hospitals. It supports three user roles — **Admin**, **Doctor**, and **Patient** — each with their own set of secured API endpoints.

All APIs are documented with **Swagger UI** and secured with **JWT (JSON Web Token)** authentication.

---

## 🌍 Real World Problem Solved

In most small and mid-sized clinics in India:
- Patients still **call the front desk** to book appointments
- Doctors maintain **paper registers**
- Admins manage everything **manually** — leading to conflicts and lost records

**MedSync solves this by:**
- ✅ Allowing patients to book appointments **online, anytime**
- ✅ Giving doctors a **digital system** to manage appointments and update status
- ✅ Giving admins **complete control** over users, doctors, and appointments
- ✅ Ensuring **data security** — a patient cannot see another patient's data

---

## ✨ Features

### 🔐 Authentication & Security
- JWT-based stateless authentication
- BCrypt password encryption
- Role-based access control (ADMIN / DOCTOR / PATIENT)
- Custom JWT filter on every request

### 👨‍⚕️ Doctor Features
- Complete doctor profile (specialization, qualification, experience, fees)
- View all appointments assigned to them
- Confirm, complete, or cancel appointments

### 🧑‍💼 Patient Features
- Complete patient profile (DOB, blood group, medical history)
- Book appointments with any doctor
- View and cancel their own appointments

### 🛡️ Admin Features
- View all registered users
- View all appointments across the system
- Delete users and doctors

### 📄 API Documentation
- Full Swagger UI at `/swagger-ui.html`
- Bearer token authorization supported in Swagger

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Security | Spring Security + JWT (JJWT 0.11.5) |
| Database | MySQL 8.0 |
| ORM | Spring Data JPA + Hibernate |
| Password Encryption | BCrypt |
| API Documentation | Springdoc OpenAPI (Swagger UI) 2.5.0 |
| Build Tool | Maven |
| IDE | Spring Tool Suite (STS) |

---

## 🏗️ Project Architecture

```
com.medsync
├── config/
│   ├── SecurityConfig.java          # Spring Security configuration
│   └── SwaggerConfig.java           # Swagger/OpenAPI configuration
├── controller/
│   ├── AuthController.java          # Register & Login APIs
│   ├── AdminController.java         # Admin-only APIs
│   ├── DoctorController.java        # Doctor APIs
│   ├── AppointmentController.java   # Appointment APIs
│   └── PatientController.java       # Patient APIs
├── dto/
│   ├── request/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── AppointmentRequest.java
│   │   ├── DoctorProfileRequest.java
│   │   └── PatientProfileRequest.java
│   └── response/
│       ├── AuthResponse.java
│       └── ApiResponse.java
├── entity/
│   ├── User.java                    # User entity (implements UserDetails)
│   ├── Doctor.java                  # Doctor profile entity
│   ├── Patient.java                 # Patient profile entity
│   ├── Appointment.java             # Appointment entity
│   ├── Role.java                    # Enum: ADMIN, DOCTOR, PATIENT
│   └── AppointmentStatus.java       # Enum: PENDING, CONFIRMED, CANCELLED, COMPLETED
├── exception/
│   ├── ResourceNotFoundException.java
│   ├── DuplicateResourceException.java
│   └── GlobalExceptionHandler.java  # @RestControllerAdvice
├── repository/
│   ├── UserRepository.java
│   ├── DoctorRepository.java
│   ├── PatientRepository.java
│   └── AppointmentRepository.java
├── security/
│   ├── filter/JwtAuthFilter.java    # JWT request filter
│   └── util/JwtUtil.java            # JWT generation & validation
└── service/impl/
    ├── AuthService.java
    ├── CustomUserDetailsService.java
    ├── DoctorService.java
    └── AppointmentService.java
```

---

## 📡 API Endpoints

### 🔓 Public APIs (No token required)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register as Admin/Doctor/Patient |
| POST | `/api/auth/login` | Login and get JWT token |

### 🛡️ Admin APIs (ADMIN token required)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/users` | Get all users |
| DELETE | `/api/admin/users/{id}` | Delete a user |
| DELETE | `/api/admin/doctors/{id}` | Delete a doctor |
| GET | `/api/admin/appointments` | View all appointments |

### 👨‍⚕️ Doctor APIs (DOCTOR token required)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/doctor/profile/complete` | Complete doctor profile |
| PUT | `/api/doctor/profile/{id}` | Update doctor profile |
| GET | `/api/doctor/appointments` | View my appointments |
| PUT | `/api/doctor/appointments/{id}/status` | Update appointment status |

### 🧑‍💼 Patient APIs (PATIENT token required)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/patient/profile/complete` | Complete patient profile |
| GET | `/api/patient/profile` | View my profile |
| POST | `/api/patient/appointments/book` | Book an appointment |
| GET | `/api/patient/appointments` | View my appointments |
| PUT | `/api/patient/appointments/{id}/cancel` | Cancel an appointment |

### 🌐 Public Doctor Search (Any authenticated user)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/doctors` | Get all doctors |
| GET | `/api/doctors/{id}` | Get doctor by ID |
| GET | `/api/doctors/specialization/{spec}` | Search by specialization |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven

### Installation

**1. Clone the repository**
```bash
git clone https://github.com/YOUR_USERNAME/MedSync-Healthcare-Management.git
cd MedSync-Healthcare-Management
```

**2. Create MySQL database**
```sql
CREATE DATABASE medsync_db;
```

**3. Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medsync_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
medsync.jwt.secret=MedSyncSuperSecretKeyForJWTTokenGenerationMustBe256BitsLong!
medsync.jwt.expiration=86400000
server.port=8080
```

**4. Run the application**
```bash
mvn spring-boot:run
```

**5. Access Swagger UI**
```
http://localhost:8080/swagger-ui.html
```

---

## 🗄️ Database Schema

```
users
├── id (PK)
├── full_name
├── email (unique)
├── password (BCrypt encrypted)
├── phone
├── role (ADMIN/DOCTOR/PATIENT)
└── created_at

doctors
├── id (PK)
├── user_id (FK → users)
├── specialization
├── qualification
├── experience_years
├── available_days
├── available_time
└── consultation_fee

patients
├── id (PK)
├── user_id (FK → users)
├── date_of_birth
├── gender
├── blood_group
├── address
└── medical_history

appointments
├── id (PK)
├── patient_id (FK → patients)
├── doctor_id (FK → doctors)
├── appointment_date
├── appointment_time
├── reason
├── status (PENDING/CONFIRMED/CANCELLED/COMPLETED)
├── doctor_notes
└── booked_at
```

---

## 🔐 Security Flow

```
Client Request
      │
      ▼
JwtAuthFilter (intercepts every request)
      │
      ├── Extract token from Authorization header
      ├── Validate token signature & expiry (JwtUtil)
      ├── Load user from CustomUserDetailsService
      └── Set Authentication in SecurityContextHolder
                    │
                    ▼
            SecurityConfig
                    │
      ┌─────────────┼─────────────┐
      ▼             ▼             ▼
   ADMIN         DOCTOR        PATIENT
/api/admin/**  /api/doctor/** /api/patient/**
```

---

## ⚔️ Challenges Solved

### 1. Circular Dependency in Spring Security
**Problem:** JwtAuthFilter → AuthService → SecurityConfig → JwtAuthFilter (circular chain)

**Solution:** Extracted `CustomUserDetailsService` as a separate class implementing `UserDetailsService`. Both SecurityConfig and JwtAuthFilter now depend on it directly — breaking the circular chain.

### 2. JSON Infinite Recursion
**Problem:** Patient → Appointments → Patient → Appointments (endless loop during JSON serialization)

**Solution:** Added `@JsonIgnore` on the `appointments` list in both `Patient` and `Doctor` entities.

### 3. Foreign Key Constraint on User Deletion
**Problem:** Cannot delete a user who has a linked patient/doctor profile due to FK constraint.

**Solution:** Delete child records (patient profile, doctor profile) before deleting the parent user in `AdminController`.

---

## 👨‍💻 Author

**Siddapuram Vamshi**
- 🎓 MCA Graduate | CGPA: 8.40
- 📍 Hyderabad, Telangana
- 💼 Java Backend Developer (Fresher)
- 🔗 [LinkedIn](https://linkedin.com/in/siddapuramvamshi/)
- 📧 vamshisiddapuram982@gmail.com

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

⭐ **If you found this project helpful, please give it a star!**