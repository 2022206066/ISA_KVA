package org.aleksa.repositories;

import org.aleksa.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepo extends JpaRepository<Director, Integer> {
    Director findByName(String name);
} 
