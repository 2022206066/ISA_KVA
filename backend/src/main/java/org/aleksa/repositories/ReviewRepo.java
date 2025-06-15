package org.aleksa.repositories;

import org.aleksa.entities.Movie;
import org.aleksa.entities.Review;
import org.aleksa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
    List<Review> findAllByMovieMovie(Movie movie);
    List<Review> findAllByUserUser(User user);
    List<Review> findReviewsByMovieMovie_Id(Integer movieId);
    List<Review> findByMovieMovieId(Integer movieId);
}
