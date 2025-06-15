package org.aleksa.repositories;

import org.aleksa.entities.Movie;
import org.aleksa.entities.MovieWatched;
import org.aleksa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieWatchedRepo extends JpaRepository<MovieWatched, Integer> {
    MovieWatched findByuserUserAndMovieMovie(User user, Movie movie);
    List<MovieWatched> findByuserUser(User user);
}
