# E-Commerce Backend API

Java 17, Spring Boot, MySQL, Redis cache, H2 tests, product CRUD, orders and role-based HTTP Basic authentication.

## Setup
1. In MySQL Workbench run `CREATE DATABASE IF NOT EXISTS ecommerce;`
2. Edit `src/main/resources/application.properties` and set your own MySQL username/password, or set `DB_USERNAME` and `DB_PASSWORD`.
3. Start Redis: `docker compose up -d` (Docker Desktop must be running).
4. Run `mvn spring-boot:run`.
5. Tests: `mvn test` (uses H2 and disables Redis caching).

## Development-only accounts
Customer: `customer` / `customer123`
Admin/store owner: `admin` / `admin123`
Change these before deployment. This starter uses HTTP Basic, not JWT; do not expose it publicly with demo credentials.

## Product API
Public: `GET /api/products`, `GET /api/products/{id}`
Admin: `POST /api/products`, `PUT /api/products/{id}`, `DELETE /api/products/{id}`
List filters: `page`, `size`, `sortBy`, `direction`, `name`, `category`.

## Orders
Customer/Admin: `POST /api/orders` with `{"productId":1,"quantity":2}`; server calculates total from the product price and decrements stock.
Customer/Admin: `GET /api/orders/my`
Admin only: `GET /api/orders`
Admin only: `PATCH /api/orders/{id}/status` with `{"status":"CONFIRMED"}`.
Statuses: `PENDING`, `CONFIRMED`, `SHIPPED`, `CANCELLED`.

In Postman use Authorization -> Basic Auth. Product reads are public. Product writes and all-order management require the admin account.

This is a portfolio starter, not production-ready: no user registration, JWT, payment processing, notifications or rate limiting. Production systems need HTTPS, proper identity management, secret management, migrations, and concurrency-safe stock updates.
