package org.aleksa.services;

import org.aleksa.entities.Actor;
import org.aleksa.repositories.ActorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepo actorRepo;

    @Autowired
    public ActorService(ActorRepo actorRepo) {
        this.actorRepo = actorRepo;
    }

    public List<Actor> getAllActors() {
        return actorRepo.findAll();
    }

    public Actor getActorById(Integer id) {
        return actorRepo.findById(id).orElse(null);
    }

    public Actor getActorByName(String name) {
        return actorRepo.findByName(name);
    }

    public Actor createActor(Actor actor) {
        return actorRepo.save(actor);
    }

    public Actor updateActor(Integer id, Actor actorDetails) {
        Optional<Actor> actorOptional = actorRepo.findById(id);
        if(actorOptional.isPresent()) {
            Actor actor = actorOptional.get();
            actor.setName(actorDetails.getName());
            actor.setBio(actorDetails.getBio());
            return actorRepo.save(actor);
        }
        return null;
    }

    public boolean deleteActor(Integer id) {
        if(actorRepo.existsById(id)) {
            actorRepo.deleteById(id);
            return true;
        }
        return false;
    }
} 
