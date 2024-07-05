# Spring Boot REST API
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/hoffmann-g/payment-project/blob/main/LICENSE)

# About
This project is a REST API application developed in Spring Boot for learning purposes. It is a money transaction system with integrated token-based authentication, including rules and permissions for admins and users.

The architecture follows the MVC pattern with entities, resources, security filters, DTOs, and uses a MySQL database through JpaRepository, along with an H2 in-memory database for testing.

# Technologies used
- Java
- Spring Boot
- Spring Boot Security
- JPA / Hibernate
- Maven
- MySQL
- H2 database

# Installation
```bash
# clone the repository:
git clone https://github.com/hoffmann-g/payment-project.git

# build the project using Maven:
mvn clean install

# run the application
mvn spring-boot:run
```

# Author
Guilherme Hoffmann Batistti dos Santos

[LinkedIn Profile](https://www.linkedin.com/in/hoffmann-g/)
