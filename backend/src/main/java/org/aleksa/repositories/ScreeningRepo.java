package org.aleksa.repositories;

import org.aleksa.entities.Movie;
import org.aleksa.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScreeningRepo extends JpaRepository<Screening, Integer> {
    List<Screening> findByMovie(Movie movie);
    List<Screening> findByScreeningDate(LocalDate date);
    List<Screening> findByMovieAndScreeningDate(Movie movie, LocalDate date);
    List<Screening> findByScreeningDateGreaterThanEqual(LocalDate date);
} 
