package org.aleksa.services;

import org.aleksa.entities.Director;
import org.aleksa.repositories.DirectorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {

    private final DirectorRepo directorRepo;

    @Autowired
    public DirectorService(DirectorRepo directorRepo) {
        this.directorRepo = directorRepo;
    }

    public List<Director> getAllDirectors() {
        return directorRepo.findAll();
    }

    public Director getDirectorById(Integer id) {
        return directorRepo.findById(id).orElse(null);
    }

    public Director getDirectorByName(String name) {
        return directorRepo.findByName(name);
    }

    public Director createDirector(Director director) {
        return directorRepo.save(director);
    }

    public Director updateDirector(Integer id, Director directorDetails) {
        Optional<Director> directorOptional = directorRepo.findById(id);
        if(directorOptional.isPresent()) {
            Director director = directorOptional.get();
            director.setName(directorDetails.getName());
            director.setBio(directorDetails.getBio());
            return directorRepo.save(director);
        }
        return null;
    }

    public boolean deleteDirector(Integer id) {
        if(directorRepo.existsById(id)) {
            directorRepo.deleteById(id);
            return true;
        }
        return false;
    }
} 
