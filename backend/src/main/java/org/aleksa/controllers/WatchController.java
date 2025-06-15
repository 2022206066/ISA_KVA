package org.aleksa.controllers;

import org.aleksa.dtos.WatchDTO;
import org.aleksa.entities.Movie;
import org.aleksa.services.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("watch")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class WatchController {

    private final WatchService watchService;

    @Autowired
    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }

    @GetMapping("all")
    public ResponseEntity<List<WatchDTO>> getAllWatchedMovies() {
        return ResponseEntity.ok(watchService.getAllWatchedMovies());
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<WatchDTO>> getWatchedMoviesByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(watchService.getWatchedMoviesByUser(userId));
    }

    @PostMapping("add")
    public ResponseEntity<WatchDTO> markMovieAsWatched(@RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        Integer movieId = request.get("movieId");
        
        if (userId == null || movieId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        WatchDTO watchedMovie = watchService.markMovieAsWatched(userId, movieId);
        if (watchedMovie != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(watchedMovie);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> removeWatchedMovie(@PathVariable Integer id) {
        boolean deleted = watchService.removeWatchedMovie(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
