# API Automation Framework
This project is an API automation framework using RestAssured, WireMock, TestNG, and Allure It demonstrates login and user APIs with support for mocking and reporting.
## Features

- **Login API**: Validates token generation and stores it in `TokenManager`.
- **User API**: Retrieves user details using the stored token.
- **WireMock**: Mock endpoints for independent testing.
- **Allure Reporting**: Step-level logging with severity, description, and structured reports.
- **Reusable Specs**: `SpecBuilder` defines request/response configurations.

## Project Structure:
client-API clients (AuthClient, Userclient)
Frameworkbase - Request/Response specifications
logintest-TestNG tests with Allure steps
models-Request/Response POJOs
utils- MockServerManager & TokenManager

