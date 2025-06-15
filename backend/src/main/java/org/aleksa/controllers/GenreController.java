package org.aleksa.controllers;

import org.aleksa.entities.Genre;
import org.aleksa.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("genre")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("{id}")
    public ResponseEntity<Genre> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @PostMapping("add")
    public ResponseEntity<Genre> post(@RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.newGenre(genre));
    }

    @PutMapping("update")
    public ResponseEntity<Genre> put(@RequestBody Genre value) {
        return ResponseEntity.ok(genreService.updateGenre(value));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String>  delete(@PathVariable Integer id) {
        if(genreService.deleteGenre(id)) return ResponseEntity.ok("Deleted genre");
        return ResponseEntity.notFound().build();
    }
}
