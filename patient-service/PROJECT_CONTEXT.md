# Patient Service Project Context

## Project Overview

This is a Spring Boot-based patient management microservice located in `patient-service`. It exposes REST endpoints for patient operations and stores patient data in an H2 in-memory database seeded from `data.sql`.

## Build & Dependencies

- `pom.xml` defines the project as a Spring Boot application with Java 21.
- Key dependencies:
  - `spring-boot-starter-web` for REST controllers
  - `spring-boot-starter-data-jpa` for persistence
  - `spring-boot-starter-validation` for request validation
  - `h2` runtime database for local testing
  - `postgresql` runtime dependency for production compatibility
  - `lombok` optional support
  - `spring-boot-devtools` for development hot reload
  - `spring-boot-starter-test` for testing support

## Configuration

- `src/main/resources/application.properties`
  - Sets `spring.datasource.url=jdbc:h2:mem:testdb`
  - Enables H2 console at `/h2-console`
  - Uses `spring.jpa.hibernate.ddl-auto=update`
  - Initializes SQL on startup with `spring.sql.init.mode=always`
  - Runs the service on port `4000`

- `src/main/resources/data.sql`
  - Creates the `patient` table if missing
  - Inserts multiple seeded patient records with fixed UUIDs

## Package and File Structure

### Main entrypoint

- `PatientServiceApplication.java`
  - Spring Boot application bootstrap class
  - Starts the embedded server and application context

### Controller layer

- `controller/PatientController.java`
  - Exposes API endpoints:
    - `GET /patients` to fetch all patients
    - `POST /patients` to create a new patient
    - `PUT /patients/{id}` to update an existing patient
  - Depends on `PatientService`
  - Uses `PatientRequestDTO` for request bodies and returns `PatientResponseDTO`

### Service layer

- `service/PatientService.java`
  - Contains business logic for patient operations
  - Uses `PatientRepository` to access the database
  - Uses `PatientMapper` to convert between DTOs and the entity model
  - Implements:
    - `getPatients()`
    - `createPatient(PatientRequestDTO)`
    - `updatePatient(UUID, PatientRequestDTO)`
  - Throws domain exceptions when validation or uniqueness rules fail

### Repository layer

- `repository/PatientRepository.java`
  - Extends `JpaRepository<Patient, UUID>`
  - Adds `existsByEmail(String email)` for duplicate email checks

### Data transfer objects (DTOs)

- `dto/PatientRequestDTO.java`
  - Represents incoming API payloads
  - Includes validation annotations for required fields
  - Fields: `name`, `email`, `address`, `dateOfBirth`, `registrationDate`

- `dto/PatientResponseDTO.java`
  - Represents outgoing API responses
  - Fields: `id`, `name`, `email`, `address`, `dateOfBirth`

### Mapper

- `mapper/PatientMapper.java`
  - Converts `PatientRequestDTO` into `Patient`
  - Converts `Patient` into `PatientResponseDTO`
  - Handles date parsing from strings to `LocalDate`

### Persistence model

- `model/Patient.java`
  - JPA entity representing a patient record
  - Persisted fields:
    - `UUID id`
    - `String name`
    - `String email` (unique)
    - `String address`
    - `LocalDate dateOfBirth`
    - `LocalDate registrationDate`
  - Uses JPA annotations for entity mapping

### Exception handling

- `exception/EmailAlreadyExistsException.java`
  - Runtime exception thrown when a patient email is already registered

- `exception/PatientNotFoundException.java`
  - Runtime exception thrown when updating a patient that does not exist

- `exception/GlobalExceptionHandler.java`
  - Handles:
    - `MethodArgumentNotValidException` for validation failures
    - `EmailAlreadyExistsException` for duplicate emails
    - `PatientNotFoundException` for missing patient records
  - Produces structured HTTP error responses

## Data Flow & Connections

1. Client sends a request to `PatientController`.
2. Controller validates the request body as `PatientRequestDTO`.
3. Controller delegates to `PatientService`.
4. `PatientService` applies business rules:
   - `createPatient` checks duplicate email via `PatientRepository.existsByEmail`
   - `updatePatient` loads the entity, updates fields, and saves it back
5. `PatientService` uses `PatientMapper` to convert between entity and DTO types.
6. `PatientRepository` interacts with the H2 database through JPA.
7. Exceptions thrown in the service layer are handled by `GlobalExceptionHandler`.
8. `PatientResponseDTO` is returned to the client.

## API Request Examples

The workspace also includes HTTP request examples under `api-requests/patient-service/`:
- `create-patient.http`
- `get-patients.http`
- `update-patient.http`

These files are useful for testing the REST API manually.

## Summary of Work Done So Far

- Implemented a Spring Boot patient service module
- Set up JPA/H2 persistence with initial seed data
- Defined REST endpoints for patient retrieval, creation, and update
- Added DTO validation and unique email enforcement
- Added centralized exception handling for validation and domain errors
- Included API request files for quick manual testing

## Notes

- The service is currently configured to use H2 in-memory database for development.
- PostgreSQL is included as a runtime dependency for later production deployment.
- The controller currently supports only list, create, and update operations; delete and retrieve-by-id are not implemented yet.
