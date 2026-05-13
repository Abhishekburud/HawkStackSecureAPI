\# HawkStack Secure REST API



A secure REST API built with Java, Spring Boot, Spring Security, Hibernate, and MySQL.



\## 🛠️ Tech Stack



\- Java 24

\- Spring Boot 4.0.6

\- Spring Security

\- Hibernate / JPA

\- MySQL

\- JWT (JSON Web Token)

\- Maven



\## 📁 Project Structure

src/main/java/secureAPI/

├── controller/

│   ├── AuthController.java

│   └── DetailsController.java

├── dto/

│   ├── LoginRequest.java

│   └── RegisterRequest.java

├── entity/

│   ├── User.java

│   └── Details.java

├── exception/

│   └── GlobalExceptionHandler.java

├── repository/

│   ├── UserRepository.java

│   └── DetailsRepository.java

├── response/

│   └── ApiResponse.java

├── security/

│   ├── SecurityConfig.java

│   ├── JwtFilter.java

│   └── JwtService.java

└── service/

├── AuthService.java

├── CustomUserDetailsService.java

├── DetailsService.java

└── DetailsServiceImpl.java



\## ⚙️ Setup



\### 1. Clone the repository

git clone https://github.com/Abhishekburud/HawkStackSecureAPI.git



\### 2. Create MySQL database

```sql

CREATE DATABASE hawkstackdb;

```



\### 3. Configure application.properties

```properties

spring.datasource.url=jdbc:mysql://localhost:3306/hawkstackdb

spring.datasource.username=root

spring.datasource.password=YOUR\_PASSWORD

app.jwt.secret=YOUR\_JWT\_SECRET

app.jwt.expiration-ms=86400000

```



\### 4. Run the project

mvn spring-boot:run



\## 🔐 Authentication APIs



\### Register

POST http://localhost:8081/auth/register

Body:

```json

{

&#x20;   "email": "test@gmail.com",

&#x20;   "password": "password123"

}

```

Response:

```json

{

&#x20;   "success": true,

&#x20;   "status": 201,

&#x20;   "message": "User registered successfully",

&#x20;   "data": null,

&#x20;   "timestamp": "2026-05-13T10:30:00"

}

```



\### Login

POST http://localhost:8081/auth/login

Body:

```json

{

&#x20;   "email": "test@gmail.com",

&#x20;   "password": "password123"

}

```

Response:

```json

{

&#x20;   "success": true,

&#x20;   "status": 200,

&#x20;   "message": "Login successful",

&#x20;   "data": "eyJhbGciOiJIUzI1NiJ9...",

&#x20;   "timestamp": "2026-05-13T10:30:00"

}

```



\### Update User

PUT http://localhost:8081/auth/update/{id}

Body:

```json

{

&#x20;   "email": "newemail@gmail.com",

&#x20;   "password": "newpassword123"

}

```



\### Delete User

DELETE http://localhost:8081/auth/delete/{id}



\## 📋 Details APIs (Protected)



> All Details APIs require JWT token in Authorization header

> ```

> Authorization: Bearer your\_jwt\_token

> ```



\### Create Details

POST http://localhost:8081/details

Body → form-data:

| Key | Type | Value |

|-----|------|-------|

| name | Text | John |

| email | Text | john@gmail.com |

| countryCode | Text | +91 |

| phone | Text | 9876543210 |

| address | Text | Bangalore |

| pdf | File | resume.pdf |

| video | File | intro.mp4 |



Response:

```json

{

&#x20;   "success": true,

&#x20;   "status": 201,

&#x20;   "message": "Details created successfully",

&#x20;   "data": {

&#x20;       "id": 1,

&#x20;       "name": "John",

&#x20;       "email": "john@gmail.com",

&#x20;       "countryCode": "+91",

&#x20;       "phone": "9876543210",

&#x20;       "address": "Bangalore",

&#x20;       "pdfPath": "uploads/pdfs/resume.pdf",

&#x20;       "videoPath": "uploads/videos/intro.mp4"

&#x20;   },

&#x20;   "timestamp": "2026-05-13T10:30:00"

}

```



\### Get All Details

GET http://localhost:8081/details



\### Get Detail By ID

GET http://localhost:8081/details/{id}



\### Update Detail

PUT http://localhost:8081/details/{id}



\### Delete Detail

DELETE http://localhost:8081/details/{id}



\## ❌ Error Responses



\### Email Already Registered

```json

{

&#x20;   "success": false,

&#x20;   "status": 409,

&#x20;   "message": "Email already registered",

&#x20;   "data": null,

&#x20;   "timestamp": "2026-05-13T10:30:00"

}

```



\### Invalid Credentials

```json

{

&#x20;   "success": false,

&#x20;   "status": 401,

&#x20;   "message": "Invalid password",

&#x20;   "data": null,

&#x20;   "timestamp": "2026-05-13T10:30:00"

}

```



\### Not Found

```json

{

&#x20;   "success": false,

&#x20;   "status": 404,

&#x20;   "message": "Details not found with id: 1",

&#x20;   "data": null,

&#x20;   "timestamp": "2026-05-13T10:30:00"

}

```



\### Unauthorized

```json

{

&#x20;   "success": false,

&#x20;   "status": 401,

&#x20;   "message": "Unauthorized",

&#x20;   "data": null,

&#x20;   "timestamp": "2026-05-13T10:30:00"

}

```



\## 🔒 Security



\- All `/details/\*\*` routes are protected with JWT

\- Passwords are encrypted using BCrypt

\- JWT token expires in 24 hours

\- Stateless session management



\## 📂 File Upload



\- PDF and Video files are stored in `uploads/` folder

\- Max file size: 100MB

\- Supported formats: PDF, MP4



\## 👨‍💻 Author



\*\*Abhishek Burud\*\*

\- GitHub: \[@Abhishekburud](https://github.com/Abhishekburud)

