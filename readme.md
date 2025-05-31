# Clock-In Service (WIP)

A Spring Boot service for managing employee work time tracking.

**⚠️ NOTE: This project is currently under development and should be considered alpha/experimental. Use at your own
risk! APIs may change frequently without notice. ⚠️**

## Overview

Clock-In Service is a backend application that allows users to log their working hours and retrieve their time records
by calendar week and year. The application uses a MongoDB database for storage and JWT authentication for security.

## ✨ Features

- User authentication and authorization using JWT tokens
- Record work hours with date and duration
- Retrieve work time entries by calendar week and year
- RESTful API for client integration

## Technology Stack

- Java 21
- Spring Boot
- Spring Security with JWT
- MongoDB
- Mongock (for MongoDB migrations)
- Maven

## API Endpoints

### Authentication

Authentication endpoints are available for user management.

### Work Times

| Method | Endpoint                               | Description                                        |
|--------|----------------------------------------|----------------------------------------------------|
| POST   | `/api/worktimes`                       | Create a new work time entry                       |
| GET    | `/api/worktimes/{calendarWeek}/{year}` | Get work time entries for a specific week and year |

## 🔌 Architecture

The project follows a hexagonal (ports and adapters) architecture:

- **Domain**: Core business logic
- **Adapters**:
    - In: HTTP controllers and request/response models
    - Out: Database repositories and document models
- **Ports**: Interfaces defining how the domain interacts with external components

## 📖 Setup and Installation

1. Ensure you have Java 21 and Maven installed
2. Make sure MongoDB is running locally. You can use the docker-compose to start a MongoDB:
   ```bash
   docker-compose up -d
   ```
   This will start a MongoDB instance on the default port (27017).
3. Clone the repository
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
6. The default user for development purposes is admin:admin
7. HTTP-Requests can be send and tested with the file: [local-http-requests.http](local-http-requests.http)

## Database

The application uses MongoDB with the following collections:

- `users` - User accounts and roles
- `worktimes` - Work time entries with indexed fields for efficient querying

## Security

The application implements JWT-based authentication with tokens stored in HTTP-only cookies for enhanced security.

## License

This project is licensed under the GNU General Public License v3.0 - see the [license.md](license.md) file for details.
