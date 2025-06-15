-- Cinema Database Schema and Initial Data

-- Drop tables if they exist (in reverse order of creation)
DROP TABLE IF EXISTS movie_watched;
DROP TABLE IF EXISTS movie_actor;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS screening;
DROP TABLE IF EXISTS movie;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS actor;
DROP TABLE IF EXISTS director;
DROP TABLE IF EXISTS user;

-- Create User table
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    admin BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create Director table
CREATE TABLE director (
    director_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create Actor table
CREATE TABLE actor (
    actor_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create Genre table
CREATE TABLE genre (
    genre_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Create Movie table
CREATE TABLE movie (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    genre_genre_id INT NOT NULL,
    director_id INT NOT NULL,
    duration INT NOT NULL,
    release_date DATE NOT NULL,
    FOREIGN KEY (genre_genre_id) REFERENCES genre(genre_id),
    FOREIGN KEY (director_id) REFERENCES director(director_id)
);

-- Create Screening table
CREATE TABLE screening (
    screening_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    date_time DATETIME NOT NULL,
    hall_number INT NOT NULL,
    available_seats INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id)
);

-- Create Reservation table
CREATE TABLE reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    screening_id INT NOT NULL,
    seat_count INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (screening_id) REFERENCES screening(screening_id)
);

-- Create Review table
CREATE TABLE review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    UNIQUE (user_id, movie_id)
);

-- Create Movie_Actor junction table
CREATE TABLE movie_actor (
    movie_id INT NOT NULL,
    actor_id INT NOT NULL,
    PRIMARY KEY (movie_id, actor_id),
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY (actor_id) REFERENCES actor(actor_id)
);

-- Create Movie_Watched table
CREATE TABLE movie_watched (
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    watch_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, movie_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id)
);

-- Insert sample data

-- Users (password is "password" hashed with bcrypt)
INSERT INTO user (username, password, email, admin) VALUES
('admin', '$2a$10$c.D3OcEM13v1KUzKU9VYrOdTK5dPtxXVXZC0u/EGmh52TtdQBXyNa', 'admin@cinema.com', TRUE),
('john', '$2a$10$c.D3OcEM13v1KUzKU9VYrOdTK5dPtxXVXZC0u/EGmh52TtdQBXyNa', 'john@example.com', FALSE),
('sarah', '$2a$10$c.D3OcEM13v1KUzKU9VYrOdTK5dPtxXVXZC0u/EGmh52TtdQBXyNa', 'sarah@example.com', FALSE);

-- Directors
INSERT INTO director (name) VALUES
('Christopher Nolan'),
('Steven Spielberg'),
('Quentin Tarantino'),
('Martin Scorsese'),
('Greta Gerwig');

-- Actors
INSERT INTO actor (name) VALUES
('Leonardo DiCaprio'),
('Tom Hanks'),
('Meryl Streep'),
('Robert Downey Jr.'),
('Scarlett Johansson'),
('Denzel Washington'),
('Viola Davis'),
('Brad Pitt'),
('Emma Stone'),
('Cillian Murphy');

-- Genres
INSERT INTO genre (name) VALUES
('Action'),
('Comedy'),
('Drama'),
('Sci-Fi'),
('Horror'),
('Romance'),
('Thriller'),
('Documentary');

-- Movies
INSERT INTO movie (name, description, genre_genre_id, director_id, duration, release_date) VALUES
('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 4, 1, 148, '2010-07-16'),
('The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 3, 4, 142, '1994-09-23'),
('Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 7, 3, 154, '1994-10-14'),
('Saving Private Ryan', 'Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines to retrieve a paratrooper whose brothers have been killed in action.', 3, 2, 169, '1998-07-24'),
('Little Women', 'Jo March reflects back and forth on her life, telling the beloved story of the March sisters - four young women, each determined to live life on her own terms.', 3, 5, 135, '2019-12-25');

-- Movie-Actor relationships
INSERT INTO movie_actor (movie_id, actor_id) VALUES
(1, 1), -- DiCaprio in Inception
(1, 10), -- Murphy in Inception
(2, 6), -- Washington in Shawshank
(3, 8), -- Pitt in Pulp Fiction
(4, 2), -- Hanks in Saving Private Ryan
(5, 3), -- Streep in Little Women
(5, 9); -- Stone in Little Women

-- Screenings
INSERT INTO screening (movie_id, date_time, hall_number, available_seats, price) VALUES
(1, '2023-10-20 18:00:00', 1, 120, 12.99),
(1, '2023-10-20 21:00:00', 1, 120, 14.99),
(2, '2023-10-20 19:00:00', 2, 80, 11.99),
(3, '2023-10-21 20:00:00', 3, 100, 13.99),
(4, '2023-10-22 17:30:00', 1, 120, 12.99),
(5, '2023-10-22 19:45:00', 2, 80, 11.99);

-- Reservations
INSERT INTO reservation (user_id, screening_id, seat_count, total_price, reservation_date) VALUES
(2, 1, 2, 25.98, '2023-10-15 10:23:45'),
(3, 3, 3, 35.97, '2023-10-16 14:45:22'),
(2, 5, 1, 12.99, '2023-10-17 09:12:38');

-- Reviews
INSERT INTO review (user_id, movie_id, rating, comment, review_date) VALUES
(2, 1, 5, 'Mind-blowing movie with incredible visuals and a complex story.', '2023-10-16 20:45:12'),
(3, 1, 4, 'Great film but a bit confusing at times.', '2023-10-17 10:32:45'),
(2, 2, 5, 'One of the best movies ever made. The story is powerful and moving.', '2023-10-18 18:22:33'),
(3, 3, 3, 'Interesting storytelling but too violent for my taste.', '2023-10-19 15:17:52');

-- Movies watched
INSERT INTO movie_watched (user_id, movie_id, watch_date) VALUES
(2, 1, '2023-10-15 22:15:00'),
(2, 2, '2023-10-16 21:45:00'),
(3, 1, '2023-10-16 19:30:00'),
(3, 3, '2023-10-18 20:10:00'); 