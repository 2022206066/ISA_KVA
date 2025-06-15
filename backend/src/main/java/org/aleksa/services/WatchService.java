package org.aleksa.services;

import org.aleksa.dtos.WatchDTO;
import org.aleksa.entities.Movie;
import org.aleksa.entities.MovieWatched;
import org.aleksa.entities.Reservation;
import org.aleksa.entities.User;
import org.aleksa.repositories.MovieRepo;
import org.aleksa.repositories.MovieWatchedRepo;
import org.aleksa.repositories.ReservationRepo;
import org.aleksa.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchService {

    private final MovieWatchedRepo movieWatchedRepo;
    private final UserRepo userRepo;
    private final MovieRepo movieRepo;
    private final ReservationRepo reservationRepo;

    @Autowired
    public WatchService(MovieWatchedRepo movieWatchedRepo, UserRepo userRepo, MovieRepo movieRepo, ReservationRepo reservationRepo) {
        this.movieWatchedRepo = movieWatchedRepo;
        this.userRepo = userRepo;
        this.movieRepo = movieRepo;
        this.reservationRepo = reservationRepo;
    }

    public List<WatchDTO> getAllWatchedMovies() {
        return movieWatchedRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<WatchDTO> getWatchedMoviesByUser(Integer userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return movieWatchedRepo.findByuserUser(user).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public WatchDTO markMovieAsWatched(Integer userId, Integer movieId) {
        Optional<User> userOptional = userRepo.findById(userId);
        Optional<Movie> movieOptional = movieRepo.findById(movieId);
        
        if (userOptional.isPresent() && movieOptional.isPresent()) {
            User user = userOptional.get();
            Movie movie = movieOptional.get();
            
            // Check if already watched
            MovieWatched existingWatch = movieWatchedRepo.findByuserUserAndMovieMovie(user, movie);
            if (existingWatch != null) {
                return convertToDTO(existingWatch);
            }
            
            // Create new watch record
            MovieWatched movieWatched = new MovieWatched();
            movieWatched.setUserUser(user);
            movieWatched.setMovieMovie(movie);
            movieWatched.setWatchedDate(LocalDateTime.now());
            
            // Find and update any related reservations
            List<Reservation> reservations = reservationRepo.findByUser(user);
            for (Reservation reservation : reservations) {
                if (reservation.getScreening().getMovie().getId().equals(movie.getId()) && 
                    reservation.getStatus() == Reservation.ReservationStatus.RESERVED) {
                    reservation.setStatus(Reservation.ReservationStatus.WATCHED);
                    reservationRepo.save(reservation);
                }
            }
            
            return convertToDTO(movieWatchedRepo.save(movieWatched));
        }
        
        return null;
    }

    public boolean removeWatchedMovie(Integer watchId) {
        if (movieWatchedRepo.existsById(watchId)) {
            movieWatchedRepo.deleteById(watchId);
            return true;
        }
        return false;
    }

    private WatchDTO convertToDTO(MovieWatched movieWatched) {
        return new WatchDTO(
                movieWatched.getId(),
                movieWatched.getUserUser().getId(),
                movieWatched.getUserUser().getUsername(),
                movieWatched.getMovieMovie().getId(),
                movieWatched.getMovieMovie().getName(),
                movieWatched.getWatchedDate()
        );
    }
}
