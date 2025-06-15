package org.aleksa.controllers;

import org.aleksa.dtos.CartSummaryDTO;
import org.aleksa.dtos.ReservationDTO;
import org.aleksa.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reservation")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("all")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getUserReservations(@PathVariable Integer userId) {
        return ResponseEntity.ok(reservationService.getUserReservations(userId));
    }

    @GetMapping("user/{userId}/status/{status}")
    public ResponseEntity<List<ReservationDTO>> getUserReservationsByStatus(
            @PathVariable Integer userId, @PathVariable String status) {
        return ResponseEntity.ok(reservationService.getUserReservationsByStatus(userId, status));
    }

    @GetMapping("cart/{userId}")
    public ResponseEntity<CartSummaryDTO> getUserCart(@PathVariable Integer userId) {
        return ResponseEntity.ok(reservationService.getUserCart(userId));
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Integer id) {
        ReservationDTO reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("create")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        if (createdReservation != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("batch")
    public ResponseEntity<List<ReservationDTO>> createReservationsBatch(@RequestBody List<ReservationDTO> reservations) {
        List<ReservationDTO> createdReservations = reservationService.createReservationBatch(reservations);
        if (createdReservations != null && !createdReservations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReservations);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("{id}/status")
    public ResponseEntity<ReservationDTO> updateReservationStatus(
            @PathVariable Integer id, @RequestBody Map<String, String> statusMap) {
        String newStatus = statusMap.get("status");
        if (newStatus == null) {
            return ResponseEntity.badRequest().build();
        }
        
        ReservationDTO updatedReservation = reservationService.updateReservationStatus(id, newStatus);
        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}/rating")
    public ResponseEntity<ReservationDTO> updateReservationRating(
            @PathVariable Integer id, @RequestBody Map<String, Byte> ratingMap) {
        Byte rating = ratingMap.get("rating");
        if (rating == null) {
            return ResponseEntity.badRequest().build();
        }
        
        ReservationDTO updatedReservation = reservationService.updateReservationRating(id, rating);
        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        boolean deleted = reservationService.deleteReservation(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 
