# Cinema Ticket System

A full-stack cinema ticket booking application with JWT authentication, movie management, and reservation system.

## Setup and Running

### Prerequisites

- JDK 21
- MySQL Server (running on port 3306)
- Node.js and npm (for frontend)

### Step 1: Database Setup

1. Make sure MySQL is running on your system
2. Create a database named `cinema_tickets` (or the application will create it automatically):
   ```sql
   CREATE DATABASE IF NOT EXISTS cinema_tickets;
   ```
3. Import the initial data:
   - Using MySQL command line: 
     ```
     mysql -u root -p cinema_tickets < [path-to-project]/ISA_KVA/backend/baza.sql
     ```
   - Or using MySQL Workbench:
     - Open MySQL Workbench and connect to your server
     - Select "File > Open SQL Script" and open the baza.sql file
     - Execute the script with the lightning bolt button
   - Or directly copy and execute the SQL statements:
     - Open the baza.sql file in a text editor
     - Copy the statements and execute them in the MySQL console
     - Make sure to execute them in the correct order (tables with foreign keys after their referenced tables)

### Step 2: Backend Setup

1. Configure your database connection in `backend/src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cinema_tickets?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=your_password_or_blank
   ```

2. Run the backend using one of these methods:
   - Use the provided batch script: `run-backend-without-properties.bat`
   - Open the project in IntelliJ and run the "Spring Boot Backend" configuration
   - Navigate to the backend directory and run:
     ```
     cd backend
     gradlew bootRun
     ```

3. Verify the backend is running by checking http://localhost:8080 in your browser
   - You should see a message like: `{"message":"Cinema API is running","status":"ok"}`

### Step 3: Frontend Setup

1. Open a new terminal window
2. Navigate to the frontend directory:
   ```
   cd ISA_KVA/frontend
   ```
3. Install dependencies:
   ```
   npm install
   ```
4. Start the Angular development server:
   ```
   npm start
   ```
   or
   ```
   ng serve
   ```
5. Access the application at http://localhost:5500

## Project Structure

The project is organized into two main components:

- `backend/` - Spring Boot RESTful API providing backend services
- `frontend/` - Angular application providing the user interface

## Key Features

- **User Authentication**: JWT-based login and registration with refresh token mechanism
- **Movie Management**: Browse, search, and filter movies by various criteria
- **Reservation System**: Book movie tickets and manage them through a dedicated interface
- **Shopping Cart**: Add multiple screenings to cart before completing the reservation process
- **My Reservations**: View and manage your reservation history with status tracking
- **Reviews and Ratings**: Leave reviews and ratings for watched movies
- **User Profiles**: Manage personal information
- **Admin Controls**: Special permissions for content management

## Technology Stack

### Backend:
- Spring Boot 3.5.0
- Java 21
- Gradle build system
- MySQL database
- JWT Authentication (auth0/java-jwt)
- Spring Data JPA

### Frontend:
- Angular 17
- TypeScript
- Angular Material
- RxJS
- Angular Signals

## Database Schema

The application includes these core tables:
- `user` - User account information
- `movie` - Movie details including title, description, and metadata
- `screening` - Movie showtimes with dates, prices, and available seats
- `reservation` - User ticket bookings with status tracking
- `review` - User reviews and ratings for movies
- `actor`, `director`, `genre` - Supporting entities for movie details
- `movie_actor`, `movie_watched` - Relationship tables

## API Endpoints

Key API endpoints include:
- `/api/auth/*` - Authentication endpoints (login, register, refresh)
- `/api/movies/*` - Movie management endpoints
- `/api/screening/*` - Screening management endpoints
- `/reservation/*` - Reservation management endpoints
- `/api/review/*` - Review management endpoints
- `/genre/*` - Genre management endpoints
- `/director/*` - Director management endpoints
- `/actor/*` - Actor management endpoints

## User Guide

### Registration and Login
1. Register with a username, email, password, and other required personal information
2. Login using your credentials
3. Authentication uses JWT tokens for secure access

### Browsing Movies
1. View all available movies on the Movies page
2. Filter movies by name or genre
3. Click on a movie to see detailed information including description, cast, and available screenings

### Making Reservations
1. Select a movie and choose an available screening
2. Add the desired number of tickets to your cart
3. Review your cart and complete the reservation process
4. View your reservations in the "My Reservations" section

### Managing Reservations
1. Access all your reservations from the "My Reservations" page
2. View reservation status (Reserved, Watched, Canceled)
3. Cancel reservations if needed
4. Rate movies after watching

## Troubleshooting

- **Java Version Issues**: Make sure you're using JDK 21.
- **Database Connection**: Verify your MySQL server is running and the credentials in `application.properties` are correct.
- **Port Conflicts**: The backend runs on port 8080 and frontend on port 5500 by default.
- **IntelliJ Configuration**: If using IntelliJ, make sure to use the "Spring Boot Backend" configuration for proper startup.
- **No Movies Showing**: If the frontend shows "No movies available", make sure you've imported the data from baza.sql.
- **API Errors**: If you see 500 errors when connecting to the backend, check the Spring Boot console for detailed error messages.

## License

This project is licensed under the MIT License - see the LICENSE file for details.