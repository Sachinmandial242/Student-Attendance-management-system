# Student Attendance Management System

## Project Description
The Student Attendance Management System is a web-based application developed to manage student records and attendance digitally. It allows a teacher or administrator to maintain student details, mark attendance as Present or Absent, and view attendance records.

The project reduces manual work and stores information in a MySQL database.

## Technologies Used
- Java 17
- JavaServer Pages (JSP)
- Java Servlets
- JDBC
- Hibernate ORM
- MySQL
- HTML
- Apache Tomcat 10.1
- NetBeans IDE
- Maven

## Features
- Add and manage student records
- Mark student attendance by date
- Record Present or Absent status
- Store and retrieve attendance information
- View attendance records
- Display attendance reports, if implemented

## Project Structure

```text
Student-Attendance-Management-System/
├── src/
│   └── main/
│       ├── java/
│       ├── resources/
│       └── webapp/
├── pom.xml
├── hibernate.cfg.xml
├── context.xml
├── nb-configuration.xml
└── README.md
```

*The exact folder structure may vary depending on the project configuration.*

## Database
**Database Name:** `attendance_db`

### Students Table
Stores student details such as ID, name, roll number, course, and semester.

### Attendance Table
Stores attendance details such as student ID, attendance date, and attendance status.

## Software Requirements
- Java JDK 17
- NetBeans IDE
- Apache Tomcat 10.1
- MySQL Server
- Maven

## How to Run the Project
1. Install Java JDK 17, NetBeans IDE, Apache Tomcat 10.1, and MySQL.
2. Open the project in NetBeans.
3. Configure the MySQL database and connection settings.
4. Verify the required Maven dependencies in `pom.xml`.
5. Build the project using Maven.
6. Deploy the application on Apache Tomcat.
7. Open the application in a web browser.

## Learning Outcomes
- Understanding Java web application development.
- Learning Servlet and JSP request processing.
- Practising database connectivity using JDBC and Hibernate.
- Understanding MySQL database design and CRUD operations.

## Developed By
**Sachin Mandial**  
MCA Data Science  
Chandigarh University

## Purpose
This project was developed for academic purposes as part of the Advanced Internet Programming (AIP) coursework.
