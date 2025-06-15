package org.aleksa.controllers;

import org.aleksa.entities.Director;
import org.aleksa.services.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("director")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Director>> getAllDirectors() {
        return ResponseEntity.ok(directorService.getAllDirectors());
    }

    @GetMapping("{id}")
    public ResponseEntity<Director> getDirectorById(@PathVariable Integer id) {
        Director director = directorService.getDirectorById(id);
        if (director != null) {
            return ResponseEntity.ok(director);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("add")
    public ResponseEntity<Director> createDirector(@RequestBody Director director) {
        return ResponseEntity.status(HttpStatus.CREATED).body(directorService.createDirector(director));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Director> updateDirector(@PathVariable Integer id, @RequestBody Director directorDetails) {
        Director updatedDirector = directorService.updateDirector(id, directorDetails);
        if (updatedDirector != null) {
            return ResponseEntity.ok(updatedDirector);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Integer id) {
        boolean deleted = directorService.deleteDirector(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 
