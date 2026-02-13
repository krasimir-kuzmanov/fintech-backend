# Changelog

All notable changes to this project will be documented in this file.
This project follows Keep a Changelog format.

## 0.0.20
- Validate `POST /auth/login` input and return HTTP `400 Bad Request` with `INVALID_REQUEST` when username or password is blank.
- Keep invalid credential semantics for non-empty input as HTTP `401 Unauthorized` with `INVALID_CREDENTIALS`.

## 0.0.19
- Reject `POST /transaction/payment` requests where `fromAccountId` equals `toAccountId` with HTTP `400 Bad Request` and error `SAME_ACCOUNT_TRANSFER`.

## 0.0.18
- Validate `POST /auth/register` input and return HTTP `400 Bad Request` with `INVALID_REQUEST` when username or password is blank.

## 0.0.17
- Return HTTP `401 Unauthorized` for `INVALID_CREDENTIALS` auth failures to align login semantics with standard API behavior.

## 0.0.16
- Enforce locale-independent JSON numeric serialization for monetary values using Locale.ROOT.
- Serialize BigDecimal values as plain decimals (no scientific notation) in API responses.

## 0.0.15
- Add test support endpoint GET /test/users/{username} for deterministic user-existence validation in UI tests.

## 0.0.14
- Add private constructor to constants-only class `AuthConstants` to prevent instantiation.

## 0.0.13
- Add test support endpoint `POST /test/reset` to clear in-memory auth, account, and transaction state.

## 0.0.12
- `POST /account/{accountId}/fund` now accepts only positive numeric amounts.

## 0.0.11
- Accept plain-text amounts for `POST /account/{accountId}/fund`.

## 0.0.10
- Added logout endpoint and token revocation enforcement.

## 0.0.9
- Enable CORS for local frontend development.

## 0.0.8
- Resolve concurrency issues

## 0.0.7
- Remove test-only endpoint for creating users.

## 0.0.6
- Add test-only endpoint to seed users for API tests.

## 0.0.5
- Enhance ReadMe file

## 0.0.4
- Fix RequestBody annotations in controllers for Swagger/OpenAPI docs.

## 0.0.3
- Add Swagger/OpenAPI (Springdoc) and API documentation annotations.

## 0.0.2
- Add project README.

## 0.0.1
- Initial project setup with Java 21 and Gradle.
