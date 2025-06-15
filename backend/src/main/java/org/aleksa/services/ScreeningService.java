package org.aleksa.services;

import org.aleksa.dtos.ScreeningDTO;
import org.aleksa.entities.Movie;
import org.aleksa.entities.Screening;
import org.aleksa.repositories.MovieRepo;
import org.aleksa.repositories.ScreeningRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private final ScreeningRepo screeningRepo;
    private final MovieRepo movieRepo;

    @Autowired
    public ScreeningService(ScreeningRepo screeningRepo, MovieRepo movieRepo) {
        this.screeningRepo = screeningRepo;
        this.movieRepo = movieRepo;
    }

    public List<ScreeningDTO> getAllScreenings() {
        return screeningRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ScreeningDTO> getUpcomingScreenings() {
        return screeningRepo.findByScreeningDateGreaterThanEqual(LocalDate.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ScreeningDTO> getScreeningsByMovie(Integer movieId) {
        Optional<Movie> movieOptional = movieRepo.findById(movieId);
        if (movieOptional.isPresent()) {
            return screeningRepo.findByMovie(movieOptional.get()).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public List<ScreeningDTO> getScreeningsByDate(LocalDate date) {
        return screeningRepo.findByScreeningDate(date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ScreeningDTO getScreeningById(Integer id) {
        Optional<Screening> screeningOptional = screeningRepo.findById(id);
        return screeningOptional.map(this::convertToDTO).orElse(null);
    }

    public ScreeningDTO createScreening(ScreeningDTO screeningDTO) {
        Optional<Movie> movieOptional = movieRepo.findById(screeningDTO.getMovieId());
        if (movieOptional.isPresent()) {
            Screening screening = convertToEntity(screeningDTO, movieOptional.get());
            return convertToDTO(screeningRepo.save(screening));
        }
        return null;
    }

    public ScreeningDTO updateScreening(Integer id, ScreeningDTO screeningDTO) {
        Optional<Screening> screeningOptional = screeningRepo.findById(id);
        if (screeningOptional.isPresent()) {
            Screening screening = screeningOptional.get();
            
            if (screeningDTO.getMovieId() != null && !screeningDTO.getMovieId().equals(screening.getMovie().getId())) {
                Optional<Movie> movieOptional = movieRepo.findById(screeningDTO.getMovieId());
                if (movieOptional.isPresent()) {
                    screening.setMovie(movieOptional.get());
                }
            }
            
            if (screeningDTO.getScreeningDate() != null) {
                screening.setScreeningDate(screeningDTO.getScreeningDate());
            }
            
            if (screeningDTO.getScreeningTime() != null) {
                screening.setScreeningTime(screeningDTO.getScreeningTime());
            }
            
            if (screeningDTO.getPrice() != null) {
                screening.setPrice(screeningDTO.getPrice());
            }
            
            if (screeningDTO.getAvailableSeats() != null) {
                screening.setAvailableSeats(screeningDTO.getAvailableSeats());
            }
            
            return convertToDTO(screeningRepo.save(screening));
        }
        return null;
    }

    public boolean deleteScreening(Integer id) {
        if (screeningRepo.existsById(id)) {
            screeningRepo.deleteById(id);
            return true;
        }
        return false;
    }

    private ScreeningDTO convertToDTO(Screening screening) {
        return new ScreeningDTO(
                screening.getId(),
                screening.getMovie().getId(),
                screening.getMovie().getName(),
                screening.getScreeningDate(),
                screening.getScreeningTime(),
                screening.getPrice(),
                screening.getAvailableSeats()
        );
    }

    private Screening convertToEntity(ScreeningDTO dto, Movie movie) {
        Screening screening = new Screening();
        screening.setMovie(movie);
        screening.setScreeningDate(dto.getScreeningDate());
        screening.setScreeningTime(dto.getScreeningTime());
        screening.setPrice(dto.getPrice());
        screening.setAvailableSeats(dto.getAvailableSeats());
        return screening;
    }
} 
