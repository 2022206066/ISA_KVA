package org.aleksa.controllers;

import org.aleksa.dtos.MovieCreateDTO;
import org.aleksa.dtos.MovieDetailsDTO;
import org.aleksa.entities.Movie;
import org.aleksa.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/movies")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Movie>> allMovies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre) {
        try {
            List<Movie> movies;
            
            if (name != null && !name.isEmpty()) {
                // If name parameter is provided, use search functionality
                movies = movieService.searchMovies(name);
            } else {
                // Otherwise get all movies
                movies = movieService.getAllMovies();
            }
            
            // If genre parameter is provided, filter by genre name
            if (genre != null && !genre.isEmpty()) {
                movies = movies.stream()
                    .filter(movie -> movie.getGenreGenre() != null && 
                            movie.getGenreGenre().getName().equalsIgnoreCase(genre))
                    .collect(Collectors.toList());
            }
            
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDetailsDTO> getMovieById(@PathVariable Integer id) {
        try {
            MovieDetailsDTO movie = movieService.getMovieDetails(id);
            if (movie != null) {
                return ResponseEntity.ok(movie);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody MovieCreateDTO movie) {
        try {
            Movie createdMovie = movieService.createMovie(movie);
            if (createdMovie != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String query) {
        try {
            return ResponseEntity.ok(movieService.searchMovies(query));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("genre/{genreId}")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable Integer genreId) {
        try {
            return ResponseEntity.ok(movieService.getMoviesByGenre(genreId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("director/{directorId}")
    public ResponseEntity<List<Movie>> getMoviesByDirector(@PathVariable Integer directorId) {
        try {
            return ResponseEntity.ok(movieService.getMoviesByDirector(directorId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("actor/{actorId}")
    public ResponseEntity<List<Movie>> getMoviesByActor(@PathVariable Integer actorId) {
        try {
            return ResponseEntity.ok(movieService.getMoviesByActor(actorId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
    
    @GetMapping("latest")
    public ResponseEntity<List<Movie>> getLatestMovies() {
        try {
            List<Movie> latestMovies = movieService.getLatestMovies();
            return ResponseEntity.ok(latestMovies);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
    
    @GetMapping("top-rated")
    public ResponseEntity<List<Movie>> getTopRatedMovies() {
        try {
            List<Movie> topRatedMovies = movieService.getTopRatedMovies();
            return ResponseEntity.ok(topRatedMovies);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
}
