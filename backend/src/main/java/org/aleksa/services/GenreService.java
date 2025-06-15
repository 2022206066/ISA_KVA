package org.aleksa.services;

import org.aleksa.entities.Genre;
import org.aleksa.repositories.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenreService {

    private final GenreRepo genreRepo;

    @Autowired
    public GenreService(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    public List<Genre> getAllGenres() {
        return genreRepo.findAll();
    }

    public Genre getGenreById(int id) {
        return genreRepo.findById(id).get();
    }

    public Genre newGenre(Genre genre) {
        return genreRepo.save(genre);
    }

    public Genre updateGenre(Genre genre) {
        if (genreRepo.existsById(genre.getId())) return genreRepo.save(genre);
        return null;
    }

    public boolean deleteGenre(int id) {
        if (genreRepo.existsById(id)) {
            genreRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
