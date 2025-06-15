# Cinema Ticket System Frontend Summary

This document provides a summary of the Angular frontend application for the Cinema Ticket System.

## Application Structure

The frontend follows a standard Angular application structure with key components organized as follows:

### Models

- **User**: User account and authentication data
- **Movie**: Movie details including title, description, director, etc.
- **Screening**: Movie screening information including date, time, and available seats
- **Reservation**: Ticket reservation data including screening, seats, and user
- **Review**: User reviews and ratings for movies

### Components

- **Home**: Landing page with featured movies and navigation
- **Login/Register**: User authentication forms
- **Movie List**: Browsable and filterable list of movies
- **Movie Detail**: Detailed view of a movie including screenings and reviews
- **Cart**: Shopping cart for ticket reservations
- **Reservation**: Seat selection and reservation confirmation
- **User Profile**: User account management

### Services

- **AuthService**: Handles user authentication and token management
- **MovieService**: Fetches movie data from the backend
- **ScreeningService**: Manages screening data and availability
- **ReservationService**: Handles ticket reservations and cart management
- **ReviewService**: Manages movie reviews and ratings

## Authentication Flow

The application uses JWT (JSON Web Token) for authentication:

1. User logs in with username/password
2. Backend validates credentials and returns a JWT token
3. Frontend stores the token in localStorage
4. Token is included in subsequent API requests via an HTTP interceptor
5. User's login state is maintained across page refreshes

## Environment Configuration

The application uses Angular environment files to manage configuration:

- `environment.ts` for development
- `environment.prod.ts` for production

## API Integration

The frontend is configured to work with the Spring Boot backend at http://localhost:9000/api. If your backend is running at a different URL, update the API URL in the environment files:

```typescript
// src/environments/environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:9000/api'
};
```

## Setup and Usage

See the [INSTALL.md](./INSTALL.md) file for detailed setup instructions.

## About the Linter Errors

The linter errors you're seeing in the IDE are primarily related to missing TypeScript type declarations for Angular and related libraries. These errors will be automatically resolved once you install the project dependencies using:

```
npm install
```

You can use the included setup scripts (`setup.bat` for Windows or `setup.sh` for Unix/Linux/Mac) to install the dependencies.

## Running the Application

After installing dependencies, start the development server:

```
npm start
```

The application will be available at http://localhost:4200 