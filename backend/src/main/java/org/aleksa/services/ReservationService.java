package org.aleksa.services;

import org.aleksa.dtos.CartSummaryDTO;
import org.aleksa.dtos.ReservationDTO;
import org.aleksa.entities.MovieWatched;
import org.aleksa.entities.Reservation;
import org.aleksa.entities.Screening;
import org.aleksa.entities.User;
import org.aleksa.repositories.MovieWatchedRepo;
import org.aleksa.repositories.ReservationRepo;
import org.aleksa.repositories.ScreeningRepo;
import org.aleksa.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepo reservationRepo;
    private final UserRepo userRepo;
    private final ScreeningRepo screeningRepo;
    private final MovieWatchedRepo movieWatchedRepo;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo, UserRepo userRepo, 
                             ScreeningRepo screeningRepo, MovieWatchedRepo movieWatchedRepo) {
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
        this.screeningRepo = screeningRepo;
        this.movieWatchedRepo = movieWatchedRepo;
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getUserReservations(Integer userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            return reservationRepo.findByUser(userOptional.get()).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public List<ReservationDTO> getUserReservationsByStatus(Integer userId, String status) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            Reservation.ReservationStatus reservationStatus = Reservation.ReservationStatus.valueOf(status);
            return reservationRepo.findByUserAndStatus(userOptional.get(), reservationStatus).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public CartSummaryDTO getUserCart(Integer userId) {
        List<ReservationDTO> reservations = getUserReservationsByStatus(userId, "RESERVED");
        BigDecimal totalPrice = reservations.stream()
                .map(ReservationDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return new CartSummaryDTO(reservations, totalPrice, reservations.size());
    }

    public ReservationDTO getReservationById(Integer id) {
        Optional<Reservation> reservationOptional = reservationRepo.findById(id);
        return reservationOptional.map(this::convertToDTO).orElse(null);
    }

    @Transactional
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Optional<User> userOptional = userRepo.findById(reservationDTO.getUserId());
        Optional<Screening> screeningOptional = screeningRepo.findById(reservationDTO.getScreeningId());
        
        if (userOptional.isPresent() && screeningOptional.isPresent()) {
            User user = userOptional.get();
            Screening screening = screeningOptional.get();
            
            // Get number of tickets from the seat number field or default to 1
            int numberOfTickets = 1;
            if (reservationDTO.getSeatNumber() != null && !reservationDTO.getSeatNumber().isEmpty()) {
                try {
                    numberOfTickets = Integer.parseInt(reservationDTO.getSeatNumber());
                } catch (NumberFormatException e) {
                    // If not a valid number, use default
                }
            }
            
            // Check if there are available seats
            if (screening.getAvailableSeats() < numberOfTickets) {
                return null; // Not enough seats available
            }
            
            // Reduce available seats
            screening.setAvailableSeats(screening.getAvailableSeats() - numberOfTickets);
            screeningRepo.save(screening);
            
            // Create reservation
            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setScreening(screening);
            reservation.setStatus(Reservation.ReservationStatus.RESERVED);
            reservation.setReservationDate(LocalDateTime.now());
            reservation.setSeatNumber(String.valueOf(numberOfTickets)); // Store numberOfTickets in seatNumber
            
            return convertToDTO(reservationRepo.save(reservation));
        }
        return null;
    }

    @Transactional
    public ReservationDTO updateReservationStatus(Integer id, String newStatus) {
        Optional<Reservation> reservationOptional = reservationRepo.findById(id);
        
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            Reservation.ReservationStatus oldStatus = reservation.getStatus();
            Reservation.ReservationStatus status = Reservation.ReservationStatus.valueOf(newStatus);
            
            reservation.setStatus(status);
            
            // If the status changes from RESERVED to CANCELED, increase available seats
            if (oldStatus == Reservation.ReservationStatus.RESERVED && status == Reservation.ReservationStatus.CANCELED) {
                Screening screening = reservation.getScreening();
                screening.setAvailableSeats(screening.getAvailableSeats() + 1);
                screeningRepo.save(screening);
            }
            
            // If the status changes to WATCHED, add to movie_watched
            if (status == Reservation.ReservationStatus.WATCHED) {
                MovieWatched movieWatched = new MovieWatched();
                movieWatched.setUserUser(reservation.getUser());
                movieWatched.setMovieMovie(reservation.getScreening().getMovie());
                movieWatched.setWatchedDate(LocalDateTime.now());
                movieWatchedRepo.save(movieWatched);
            }
            
            return convertToDTO(reservationRepo.save(reservation));
        }
        return null;
    }

    public ReservationDTO updateReservationRating(Integer id, Byte rating) {
        Optional<Reservation> reservationOptional = reservationRepo.findById(id);
        
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            
            // Only allow rating for WATCHED reservations
            if (reservation.getStatus() == Reservation.ReservationStatus.WATCHED) {
                reservation.setRating(rating);
                return convertToDTO(reservationRepo.save(reservation));
            }
        }
        return null;
    }

    @Transactional
    public boolean deleteReservation(Integer id) {
        Optional<Reservation> reservationOptional = reservationRepo.findById(id);
        
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            
            // Only allow deletion if the reservation is in RESERVED status
            if (reservation.getStatus() == Reservation.ReservationStatus.RESERVED) {
                // Increase available seats
                Screening screening = reservation.getScreening();
                screening.setAvailableSeats(screening.getAvailableSeats() + 1);
                screeningRepo.save(screening);
                
                reservationRepo.delete(reservation);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public List<ReservationDTO> createReservationBatch(List<ReservationDTO> reservationDTOs) {
        return reservationDTOs.stream()
                .map(this::createReservation)
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        Screening screening = reservation.getScreening();
        
        return new ReservationDTO(
                reservation.getId(),
                reservation.getUser().getId(),
                screening.getId(),
                screening.getMovie().getName(),
                screening.getScreeningDate(),
                screening.getScreeningTime(),
                reservation.getStatus().toString(),
                reservation.getRating(),
                reservation.getReservationDate(),
                reservation.getSeatNumber(),
                screening.getPrice()
        );
    }
} 
