package org.aleksa.controllers;

import org.aleksa.dtos.ScreeningDTO;
import org.aleksa.services.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/screening")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class ScreeningController {

    private final ScreeningService screeningService;

    @Autowired
    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping("all")
    public ResponseEntity<List<ScreeningDTO>> getAllScreenings() {
        return ResponseEntity.ok(screeningService.getAllScreenings());
    }

    @GetMapping("upcoming")
    public ResponseEntity<List<ScreeningDTO>> getUpcomingScreenings() {
        return ResponseEntity.ok(screeningService.getUpcomingScreenings());
    }

    @GetMapping("movie/{movieId}")
    public ResponseEntity<List<ScreeningDTO>> getScreeningsByMovie(@PathVariable Integer movieId) {
        return ResponseEntity.ok(screeningService.getScreeningsByMovie(movieId));
    }

    @GetMapping("date/{date}")
    public ResponseEntity<List<ScreeningDTO>> getScreeningsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(screeningService.getScreeningsByDate(date));
    }

    @GetMapping("{id}")
    public ResponseEntity<ScreeningDTO> getScreeningById(@PathVariable Integer id) {
        ScreeningDTO screening = screeningService.getScreeningById(id);
        if (screening != null) {
            return ResponseEntity.ok(screening);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("add")
    public ResponseEntity<ScreeningDTO> createScreening(@RequestBody ScreeningDTO screeningDTO) {
        ScreeningDTO createdScreening = screeningService.createScreening(screeningDTO);
        if (createdScreening != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdScreening);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ScreeningDTO> updateScreening(@PathVariable Integer id, @RequestBody ScreeningDTO screeningDTO) {
        ScreeningDTO updatedScreening = screeningService.updateScreening(id, screeningDTO);
        if (updatedScreening != null) {
            return ResponseEntity.ok(updatedScreening);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteScreening(@PathVariable Integer id) {
        boolean deleted = screeningService.deleteScreening(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 
