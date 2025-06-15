package org.aleksa.services;

import org.aleksa.dtos.MovieCreateDTO;
import org.aleksa.dtos.MovieDetailsDTO;
import org.aleksa.dtos.ReviewDTO;
import org.aleksa.dtos.ScreeningDTO;
import org.aleksa.entities.*;
import org.aleksa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepo movieRepo;
    private final GenreRepo genreRepo;
    private final DirectorRepo directorRepo;
    private final ActorRepo actorRepo;
    private final MovieActorRepo movieActorRepo;
    private final ReviewRepo reviewRepo;
    private final ScreeningService screeningService;
    private final ReviewService reviewService;

    @Autowired
    public MovieService(MovieRepo movieRepo, GenreRepo genreRepo, DirectorRepo directorRepo, 
                        ActorRepo actorRepo, MovieActorRepo movieActorRepo, 
                        ReviewRepo reviewRepo, ScreeningService screeningService,
                        ReviewService reviewService) {
        this.movieRepo = movieRepo;
        this.genreRepo = genreRepo;
        this.directorRepo = directorRepo;
        this.actorRepo = actorRepo;
        this.movieActorRepo = movieActorRepo;
        this.reviewRepo = reviewRepo;
        this.screeningService = screeningService;
        this.reviewService = reviewService;
    }

    public List<Movie> getAllMovies() {
        try {
            return movieRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public MovieDetailsDTO getMovieDetails(Integer id) {
        try {
            Optional<Movie> movieOptional = movieRepo.findById(id);
            if (movieOptional.isPresent()) {
                Movie movie = movieOptional.get();
                
                MovieDetailsDTO dto = new MovieDetailsDTO();
                dto.setId(movie.getId());
                dto.setName(movie.getName());
                dto.setDescription(movie.getDescription());
                
                // Safe getters with null checks
                if (movie.getGenreGenre() != null) {
                    dto.setGenre(movie.getGenreGenre().getName());
                }
                
                if (movie.getDirector() != null) {
                    dto.setDirector(movie.getDirector().getName());
                    dto.setDirectorBio(movie.getDirector().getBio());
                }
                
                dto.setDuration(movie.getDuration());
                dto.setReleaseDate(movie.getReleaseDate());
                
                // Get actors
                try {
                    List<String> actors = movieActorRepo.findByMovie(movie).stream()
                            .map(ma -> ma.getActor().getName())
                            .collect(Collectors.toList());
                    dto.setActors(actors);
                } catch (Exception e) {
                    e.printStackTrace();
                    dto.setActors(new ArrayList<>());
                }
                
                // Get reviews
                try {
                    List<ReviewDTO> reviews = reviewService.getReviewsByMovie(movie.getId());
                    dto.setReviews(reviews);
                    
                    // Calculate average rating
                    if (!reviews.isEmpty()) {
                        double avgRating = reviews.stream()
                                .mapToInt(ReviewDTO::getRating)
                                .average()
                                .orElse(0.0);
                        dto.setAverageRating(avgRating);
                    } else {
                        dto.setAverageRating(0.0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dto.setReviews(new ArrayList<>());
                    dto.setAverageRating(0.0);
                }
                
                // Get screenings
                try {
                    List<ScreeningDTO> screenings = screeningService.getScreeningsByMovie(movie.getId());
                    dto.setScreenings(screenings);
                } catch (Exception e) {
                    e.printStackTrace();
                    dto.setScreenings(new ArrayList<>());
                }
                
                return dto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public Movie createMovie(MovieCreateDTO movieDTO) {
        try {
            Movie movie = new Movie();
            movie.setName(movieDTO.getName());
            movie.setDescription(movieDTO.getDescription());
            
            // Set genre
            Optional<Genre> genreOptional = genreRepo.findById(movieDTO.getGenreId());
            if (genreOptional.isPresent()) {
                movie.setGenreGenre(genreOptional.get());
            }
            
            // Set director
            Optional<Director> directorOptional = directorRepo.findById(movieDTO.getDirectorId());
            if (directorOptional.isPresent()) {
                movie.setDirector(directorOptional.get());
            }
            
            movie.setDuration(movieDTO.getDuration());
            movie.setReleaseDate(movieDTO.getReleaseDate());
            
            // Save movie first to get the ID
            Movie savedMovie = movieRepo.save(movie);
            
            // Add actors
            if (movieDTO.getActorIds() != null && !movieDTO.getActorIds().isEmpty()) {
                for (Integer actorId : movieDTO.getActorIds()) {
                    Optional<Actor> actorOptional = actorRepo.findById(actorId);
                    if (actorOptional.isPresent()) {
                        MovieActor movieActor = new MovieActor();
                        movieActor.setMovie(savedMovie);
                        movieActor.setActor(actorOptional.get());
                        movieActorRepo.save(movieActor);
                    }
                }
            }
            
            return savedMovie;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Movie> searchMovies(String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                return getAllMovies();
            }
            
            String lowercaseQuery = query.toLowerCase();
            
            return movieRepo.findAll().stream()
                    .filter(movie -> {
                        try {
                            return movie.getName().toLowerCase().contains(lowercaseQuery) ||
                                   movie.getDescription().toLowerCase().contains(lowercaseQuery) ||
                                   (movie.getGenreGenre() != null && movie.getGenreGenre().getName().toLowerCase().contains(lowercaseQuery)) ||
                                   (movie.getDirector() != null && movie.getDirector().getName().toLowerCase().contains(lowercaseQuery));
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Movie> getMoviesByGenre(Integer genreId) {
        try {
            Optional<Genre> genreOptional = genreRepo.findById(genreId);
            if (genreOptional.isPresent()) {
                Genre genre = genreOptional.get();
                return movieRepo.findAll().stream()
                        .filter(movie -> movie.getGenreGenre() != null && movie.getGenreGenre().getId().equals(genre.getId()))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Movie> getMoviesByDirector(Integer directorId) {
        try {
            Optional<Director> directorOptional = directorRepo.findById(directorId);
            if (directorOptional.isPresent()) {
                Director director = directorOptional.get();
                return movieRepo.findAll().stream()
                        .filter(movie -> movie.getDirector() != null && movie.getDirector().getId().equals(director.getId()))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Movie> getMoviesByActor(Integer actorId) {
        try {
            Optional<Actor> actorOptional = actorRepo.findById(actorId);
            if (actorOptional.isPresent()) {
                Actor actor = actorOptional.get();
                List<MovieActor> movieActors = movieActorRepo.findByActor(actor);
                
                return movieActors.stream()
                        .map(MovieActor::getMovie)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public List<Movie> getLatestMovies() {
        try {
            // Return the 5 most recent movies by release date
            return movieRepo.findAll().stream()
                    .sorted(Comparator.comparing(Movie::getReleaseDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<Movie> getTopRatedMovies() {
        try {
            List<Movie> allMovies = movieRepo.findAll();
            
            // Calculate average rating for each movie
            return allMovies.stream()
                    .peek(movie -> {
                        try {
                            // Calculate average rating
                            List<Review> reviews = reviewRepo.findByMovieMovieId(movie.getId());
                            if (reviews != null && !reviews.isEmpty()) {
                                double avgRating = reviews.stream()
                                        .mapToInt(Review::getRating)
                                        .average()
                                        .orElse(0.0);
                                movie.setAverageRating(avgRating);
                            } else {
                                movie.setAverageRating(0.0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            movie.setAverageRating(0.0);
                        }
                    })
                    .sorted(Comparator.comparing(Movie::getAverageRating, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
