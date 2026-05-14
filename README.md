# Hermes

> Themed e-commerce platform inspired by the Greek god of commerce, messenger of the gods, known for his winged boots.

## About

**Hermes** is an e-commerce marketplace where users can create their own store, manage products, and showcase them on a public feed accessible to all visitors. Built as an MVP for a Software Engineering course.

## Features

- 🏛️ **Public Feed** — Browse products from all stores without login
- 🏪 **My Store** — Authenticated area for full product management (CRUD)
- 🔐 **Authentication** — Sign up with email verification and JWT login
- 🛒 **Cart** — Coming soon

## Tech Stack

- **Backend:** Java 25, Spring Boot 4, Spring Security, JPA/Hibernate
- **Database:** PostgreSQL (Docker)
- **Frontend:** HTML, CSS, JavaScript
- **Auth:** JWT

## Quick Start

### Prerequisites

- **Java JDK 25** — [Download](https://jdk.java.net/25/)
- **Docker Desktop** — [Download](https://www.docker.com/products/docker-desktop/)
- **Git** — [Download](https://git-scm.com/downloads)

### 1. Clone and start the database

```bash
git clone https://github.com/seu-usuario/hermes.git
cd hermes
docker compose up -d
```

### 2. Configure the application

The file `application.properties` contains credentials (JWT secret and email password) and is **not tracked by Git**. Request it from one of the project maintainers and place it at:

```
backend/src/main/resources/application.properties
```

> ⚠️ **Do not commit this file.** The `.gitignore` already excludes it.

### 3. Run the backend

```bash
cd backend
```

```bash
# macOS / Linux
./mvnw spring-boot:run

# Windows (CMD)
mvnw.cmd spring-boot:run

# Windows (PowerShell)
.\mvnw.cmd spring-boot:run
```

### 4. Open the frontend

Open `frontend/pages/index.html` in your browser.

## Usage

1. Browse the public feed
2. Sign up and verify your email
3. Log in
4. Create your store in **My Store**
5. Add and manage your products

## License

This project was developed for academic purposes.
