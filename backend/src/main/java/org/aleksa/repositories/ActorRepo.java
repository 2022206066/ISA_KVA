package org.aleksa.repositories;

import org.aleksa.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepo extends JpaRepository<Actor, Integer> {
    Actor findByName(String name);
} 
