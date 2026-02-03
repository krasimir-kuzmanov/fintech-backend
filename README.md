# fintech-backend

Spring Boot backend for the Fintech project.

**Tech Stack**
- Java: 21 (Gradle toolchain)
- Build: Gradle Wrapper 9.3
- Framework: Spring Boot 4.0.2
- Persistence: Spring Data JPA
- Database: H2 (in-memory)

**Project Layout**
- Application entrypoint: `src/main/java/com/example/fintech/fintechbackend/FintechBackendApplication.java`
- Configuration: `src/main/resources/application.yaml`

**Runtime Configuration (application.yaml)**
- App name: `fintech-backend`
- Server port: `8080`
- H2 console: enabled at `/h2-console`
- Datasource URL: `jdbc:h2:mem:fintechdb`
- Hibernate DDL: `create-drop`
- SQL logging: `show-sql: true`

## SDK
- Java SDK: JDK 21 (Gradle toolchain targets Java 21)

## Prerequisites
- JDK 21 installed
- No local Gradle install required (wrapper included)

## Run Locally
From the project root:

```bash
./gradlew bootRun
```

App starts at:
- `http://localhost:8080`

H2 console:
- `http://localhost:8080/h2-console`

## Common Gradle Tasks
- `./gradlew clean` - clean build outputs
- `./gradlew bootRun` - run the app
- `./gradlew build` - build jar and run tests

## Notes
- The database is in-memory and resets on each restart.
- Hibernate `create-drop` will create schema on startup and drop on shutdown.