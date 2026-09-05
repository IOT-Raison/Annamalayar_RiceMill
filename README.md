# Rice Mill Monitoring System - Backend

Spring Boot backend for monitoring the rice mill process:
Pre-processing → Thombai (6 bins) → Dryer → Huller

## Tech stack
- Java 17
- Spring Boot 3.3.4
- Spring Web, Spring Data JPA, Spring Security, Spring WebSocket
- MySQL

## Project structure
```
src/main/java/com/ricemill/
 ├── entity        # JPA entities (Batch, StageRecord, Bin, Machine, Alert...)
 ├── repository    # Spring Data JPA repositories
 ├── service       # Business logic
 ├── controller    # REST controllers (Dashboard, Reports, Batch, Bin...)
 ├── dto           # Request/response DTOs
 ├── config        # Security, CORS, WebSocket config
 └── exception     # Global exception handling
```

## Setup

1. Create a MySQL database (or let it auto-create via the datasource URL):
   ```sql
   CREATE DATABASE ricemill_db;
   ```

2. Update credentials in `src/main/resources/application.properties`:
   ```
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. Build and run:
   ```bash
   mvn spring-boot:run
   ```

4. Verify it's running:
   ```
   GET http://localhost:8080/api/health
   ```

## Status
✅ Base project scaffolded, health check endpoint working
⬜ Entities, repositories, services, controllers — to be added next based on requirements
