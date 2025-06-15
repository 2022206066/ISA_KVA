package org.aleksa.controllers;

import org.aleksa.entities.Actor;
import org.aleksa.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("actor")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Actor>> getAllActors() {
        return ResponseEntity.ok(actorService.getAllActors());
    }

    @GetMapping("{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Integer id) {
        Actor actor = actorService.getActorById(id);
        if (actor != null) {
            return ResponseEntity.ok(actor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("add")
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actorService.createActor(actor));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Integer id, @RequestBody Actor actorDetails) {
        Actor updatedActor = actorService.updateActor(id, actorDetails);
        if (updatedActor != null) {
            return ResponseEntity.ok(updatedActor);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Integer id) {
        boolean deleted = actorService.deleteActor(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 
