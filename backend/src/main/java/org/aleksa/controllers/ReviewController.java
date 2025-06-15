package org.aleksa.controllers;

import org.aleksa.dtos.ReviewCreateDTO;
import org.aleksa.dtos.ReviewDTO;
import org.aleksa.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/review")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("all")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("movie/{movieId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByMovie(@PathVariable Integer movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByMovie(movieId));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @PostMapping("add")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewCreateDTO review) {
        ReviewDTO createdReview = reviewService.createReview(review);
        if (createdReview != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        boolean deleted = reviewService.deleteReview(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
