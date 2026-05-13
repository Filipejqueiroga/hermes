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

**Prerequisites:** Java JDK 25, Docker Desktop, Git

```bash
git clone https://github.com/seu-usuario/hermes.git
cd hermes
docker compose up -d
```

Copy the example config file and fill in your credentials:

```bash
cp backend/src/main/resources/application.properties.example backend/src/main/resources/application.properties
```

> See the comments inside `application.properties.example` for instructions on each field. The database fields are already filled in and match `docker-compose.yml` — you only need to configure **JWT** and **Email**.

Run the backend:

```bash
cd backend
./mvnw spring-boot:run
```

Open `frontend/pages/index.html` in your browser.

## Usage

1. Browse the public feed
2. Sign up and verify your email
3. Log in
4. Create your store in **My Store**
5. Add and manage your products

## License

This project was developed for academic purposes.
