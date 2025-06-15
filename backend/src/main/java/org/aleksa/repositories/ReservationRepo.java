package org.aleksa.repositories;

import org.aleksa.entities.Reservation;
import org.aleksa.entities.Screening;
import org.aleksa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUser(User user);
    List<Reservation> findByUserAndStatus(User user, Reservation.ReservationStatus status);
    List<Reservation> findByScreening(Screening screening);
    Reservation findByUserAndScreening(User user, Screening screening);
} 
