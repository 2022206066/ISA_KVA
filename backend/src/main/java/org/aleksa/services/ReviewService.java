package org.aleksa.services;

import org.aleksa.dtos.ReviewCreateDTO;
import org.aleksa.dtos.ReviewDTO;
import org.aleksa.entities.Movie;
import org.aleksa.entities.Review;
import org.aleksa.entities.User;
import org.aleksa.repositories.MovieRepo;
import org.aleksa.repositories.MovieWatchedRepo;
import org.aleksa.repositories.ReviewRepo;
import org.aleksa.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo;
    private final MovieRepo movieRepo;
    private final MovieWatchedRepo movieWatchedRepo;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo, UserRepo userRepo, MovieRepo movieRepo, MovieWatchedRepo movieWatchedRepo) {
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
        this.movieRepo = movieRepo;
        this.movieWatchedRepo = movieWatchedRepo;
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepo.findAll().stream().map(this::reviewToDto).collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByMovie(Integer movieId) {
        Optional<Movie> movieOptional = movieRepo.findById(movieId);
        if(movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            return reviewRepo.findAllByMovieMovie(movie).stream()
                    .map(this::reviewToDto)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public List<ReviewDTO> getReviewsByUser(Integer userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return reviewRepo.findAllByUserUser(user).stream()
                    .map(this::reviewToDto)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public ReviewDTO createReview(ReviewCreateDTO reviewCreateDTO) {
        Optional<User> userOptional = userRepo.findById(reviewCreateDTO.getUserId());
        Optional<Movie> movieOptional = movieRepo.findById(reviewCreateDTO.getMovieId());

        // Verify if user has watched the movie
        if(userOptional.isPresent() && movieOptional.isPresent()) {
            User user = userOptional.get();
            Movie movie = movieOptional.get();

            // Check if user has watched this movie
            if(movieWatchedRepo.findByuserUserAndMovieMovie(user, movie) != null) {
                Review review = new Review();
                review.setUserUser(user);
                review.setMovieMovie(movie);
                review.setReviewContent(reviewCreateDTO.getReviewContent());
                review.setIsPositive(reviewCreateDTO.getIsPositive() ? (byte) 1 : (byte) 0);
                review.setReviewDate(LocalDateTime.now());
                review.setRating(reviewCreateDTO.getRating());

                return reviewToDto(reviewRepo.save(review));
            }
        }
        return null;
    }

    public boolean deleteReview(Integer reviewId) {
        Optional<Review> reviewOptional = reviewRepo.findById(reviewId);
        if(reviewOptional.isPresent()) {
            reviewRepo.delete(reviewOptional.get());
            return true;
        }
        return false;
    }

    private ReviewDTO reviewToDto(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getReviewContent(),
                review.getIsPositive() > 0,
                review.getMovieMovie().getId(),
                review.getMovieMovie().getName(),
                review.getUserUser().getId(),
                review.getUserUser().getUsername(),
                review.getReviewDate(),
                review.getRating()
        );
    }
}
