package org.aleksa.repositories;

import org.aleksa.entities.Actor;
import org.aleksa.entities.Movie;
import org.aleksa.entities.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieActorRepo extends JpaRepository<MovieActor, Integer> {
    List<MovieActor> findByMovie(Movie movie);
    List<MovieActor> findByActor(Actor actor);
    MovieActor findByMovieAndActor(Movie movie, Actor actor);
} 
