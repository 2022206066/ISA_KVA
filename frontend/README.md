# Cinema Ticket System Frontend

This is the Angular frontend for the Cinema Ticket System, a web application for browsing movies, viewing screenings, and purchasing tickets online.

## Prerequisites

- **Node.js**: v16 or newer
- **npm**: v7 or later

## Project Structure

The frontend follows a feature-based organization:

```
src/
├── app/
│   ├── components/        # UI components organized by feature
│   │   ├── home/          # Landing page component
│   │   ├── login/         # Authentication components
│   │   ├── register/      # User registration
│   │   ├── movie-list/    # Movie browsing components
│   │   ├── movie-detail/  # Movie details and screenings
│   │   └── cart/          # Shopping cart for reservations
│   ├── guards/            # Route guards for authentication
│   ├── interceptors/      # HTTP interceptors for JWT
│   ├── models/            # TypeScript interfaces
│   └── services/          # API services
├── environments/          # Environment configuration
└── assets/                # Static assets (images, fonts, etc.)
```

## Setup and Installation

1. Navigate to the `frontend` directory
2. Install dependencies:
   ```
   npm install
   ```
3. Start the development server:
   ```
   npm start
   ```
4. The frontend will be available at `http://localhost:5500`

## IntelliJ IDEA Integration

For IntelliJ IDEA users:

1. Open the project in IntelliJ IDEA
2. Navigate to the frontend directory
3. Right-click on package.json and select "Show npm Scripts"
4. Run the "start" script to launch the application

## Features

- **User Authentication**: Register, login, and profile management
- **Movie Browsing**: View all movies with filtering by name and genre
- **Movie Details**: View movie information, screenings, and reviews
- **Ticket Reservations**: Select screenings and purchase tickets
- **Shopping Cart**: Add and manage movie tickets before checkout
- **User Reviews**: Submit and view movie reviews
- **Admin Panel**: Manage movies and screenings (admin users only)

## Components

- **Home**: Landing page with featured movies
- **Login/Register**: User authentication forms
- **Movie List**: Browsable and filterable list of movies
- **Movie Detail**: Detailed view of a movie including screenings and reviews
- **Cart**: Shopping cart for ticket reservations
- **User Profile**: User account management

## Services

- **AuthService**: Handles user authentication and token management
- **MovieService**: Fetches movie data from the backend
- **ScreeningService**: Manages screening data and availability
- **ReservationService**: Handles ticket reservations and cart management
- **ReviewService**: Manages movie reviews and ratings

## Models

- **User**: User account and authentication data
- **Movie**: Movie details including title, description, director, etc.
- **Screening**: Movie screening information including date, time, and available seats
- **Reservation**: Ticket reservation data including screening, seats, and user
- **Review**: User reviews and ratings for movies

## Backend Communication

The frontend is configured to communicate with the backend API running at http://localhost:8080/api. If your backend is running on a different URL, update the API URL in the environment files:

- src/environments/environment.ts
- src/environments/environment.prod.ts

## Development

- Run `npm start` for a development server
- Navigate to `http://localhost:4200/`
- The application will automatically reload if you change any of the source files

## Building for Production

To build the application for production:

```
npm run build
```

The build artifacts will be stored in the `dist/` directory. 