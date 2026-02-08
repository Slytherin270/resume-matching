# Resume Matching

## Architecture overview

This codebase follows **Domain-Driven Design (DDD)** and **SOLID** principles to keep domain logic clear, reduce coupling, and make each module easier to evolve.

### Backend (Spring Boot)

Each bounded context (auth, user, resume, matching, audit) is organized into layered subpackages:

- `domain` – core entities, value objects, and domain events.
- `application` – use cases and orchestration logic (services).
- `infrastructure` – adapters and external integrations (JPA repositories, Redis stores, etc.).
- `interfaces` – API layer (controllers, request/response DTOs, mappers).

Example structure:

```
backend/src/main/java/com/example/resumematching/resume/
  domain/         # Resume, ResumeUploadedEvent
  application/    # ResumeService
  infrastructure/ # ResumeRepository (JPA)
  interfaces/     # ResumeController, ResumeMapper, DTOs
```

**Why this helps:**
- The **domain** stays focused on business concepts (e.g., `Resume`, `AuditEntry`).
- The **application** layer expresses use cases (e.g., `ResumeService` orchestrating uploads and events).
- The **infrastructure** layer isolates persistence and external services (e.g., `RefreshTokenStore`).
- The **interfaces** layer keeps HTTP concerns separate from business logic.

### Frontend (React)

The frontend is already feature-oriented (`features/auth`, `features/dashboard`, etc.). This aligns with DDD’s bounded-context concept by keeping UI, API calls, and slices grouped by capability rather than by technical type.

## SOLID applied in practice

- **Single Responsibility Principle (SRP):**
  - Controllers only handle HTTP input/output.
  - Services focus on use-case orchestration.
  - Repositories and stores handle persistence.

- **Open/Closed Principle (OCP):**
  - New persistence strategies can be added behind repository/store interfaces without changing services.

- **Liskov Substitution Principle (LSP):**
  - Infrastructure implementations can be swapped (e.g., alternative token stores) without affecting consumers.

- **Interface Segregation Principle (ISP):**
  - DTOs are small and purpose-specific; services depend only on the interfaces they need.

- **Dependency Inversion Principle (DIP):**
  - Application services depend on abstractions (repositories, token stores) and not on concrete details.

## Why DDD + SOLID here

This project includes multiple workflows (authentication, resume processing, matching, auditing). DDD keeps boundaries clear as the system grows, while SOLID keeps each class and dependency focused and composable. Together they minimize ripple effects when adding new features, such as new matching algorithms or auditing requirements.
