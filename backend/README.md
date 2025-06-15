# Cinema Ticket System Backend

This is the Spring Boot backend for the Cinema Ticket System, providing a RESTful API for the Angular frontend.

## Features

- **RESTful API**: Complete API for cinema ticket system operations
- **JWT Authentication**: Secure authentication with access and refresh tokens
- **Role-based Authorization**: Different access levels for users and administrators
- **Database Integration**: Spring Data JPA with MySQL
- **Entity Relationships**: OneToMany and ManyToMany relationships between entities

## Prerequisites

- **Java**: JDK 21
- **MySQL**: Running instance with a database named 'cinema_tickets'

## Project Structure

The backend follows a layered architecture with clear separation of concerns:

```
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── aleksa/
│   │           ├── Application.java     - Main application entry point
│   │           ├── controllers/         - REST controllers handling HTTP requests
│   │           ├── services/            - Business logic implementation
│   │           ├── repositories/        - Data access interfaces
│   │           ├── entities/            - JPA entity classes
│   │           ├── dtos/                - Data Transfer Objects
│   │           ├── mappers/             - Object mapping utilities
│   │           ├── security/            - Authentication and authorization
│   │           ├── exceptions/          - Custom exception handling
│   │           └── utils/               - Utility classes
│   └── resources/
│       ├── application.properties       - Application configuration
│       └── sql/                         - SQL scripts
└── test/
    └── java/                            - Test classes
```

## Database Schema

The database includes the following tables:

- `user` - User information and authentication data
- `movie` - Movie details (title, description, duration, etc.)
- `director` - Director information
- `actor` - Actor information
- `genre` - Movie genres
- `movie_actor` - Many-to-many relationship between movies and actors
- `screening` - Movie screenings/showtimes
- `reservation` - Ticket reservations
- `review` - User reviews for movies
- `movie_watched` - Records of movies watched by users

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/refresh` - Refresh access token

### Movies
- `GET /api/movies` - Get all movies
- `GET /api/movies/{id}` - Get movie by ID
- `GET /api/movies/search` - Search movies by criteria
- `POST /api/movies` - Create a new movie (admin only)
- `PUT /api/movies/{id}` - Update a movie (admin only)
- `DELETE /api/movies/{id}` - Delete a movie (admin only)

### Screenings
- `GET /api/screenings` - Get all screenings
- `GET /api/screenings/{id}` - Get screening by ID
- `POST /api/screenings` - Create a new screening (admin only)
- `PUT /api/screenings/{id}` - Update a screening (admin only)
- `DELETE /api/screenings/{id}` - Delete a screening (admin only)

### Reservations
- `GET /api/reservations` - Get user's reservations
- `GET /api/reservations/{id}` - Get reservation details
- `POST /api/reservations` - Create a new reservation
- `PUT /api/reservations/{id}` - Update reservation status
- `DELETE /api/reservations/{id}` - Cancel reservation

### Reviews
- `GET /api/reviews/movie/{movieId}` - Get all reviews for a movie
- `POST /api/reviews` - Submit a new review
- `PUT /api/reviews/{id}` - Update a review
- `DELETE /api/reviews/{id}` - Delete a review

## Setup and Running

1. Configure your MySQL database in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cinema_tickets?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=
   ```

2. Run the application:
   ```
   gradlew bootRun
   ```
   
   Alternatively, use the provided batch script:
   ```
   run-backend-fixed.bat
   ```

3. The API will be available at `http://localhost:8080/api`

## Authentication Flow

The authentication system uses JWT tokens with the following flow:

1. User logs in with username/password and receives an access token and refresh token
2. Access token is used for API authorization (valid for 1 hour)
3. When the access token expires, the client can use the refresh token to obtain a new access token
4. Refresh tokens have a longer lifespan (30 days)

## Security Implementation

Security is implemented without Spring Security, using custom interceptors:
- `UserAuth` - Ensures the user is authenticated for protected endpoints
- `AdminAuth` - Ensures the user has admin role for admin-only endpoints

The JWT validation is handled by the `JwtUtil` class, which uses RSA public/private key pairs 