package org.aleksa.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @Column(name = "is_positive", nullable = false)
    private Byte isPositive;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_movie_id", nullable = false)
    private Movie movieMovie;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User userUser;

    @Column(name = "review_date", nullable = false)
    private LocalDateTime reviewDate;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Byte getIsPositive() {
        return isPositive;
    }

    public void setIsPositive(Byte isPositive) {
        this.isPositive = isPositive;
    }

    public Movie getMovieMovie() {
        return movieMovie;
    }

    public void setMovieMovie(Movie movieMovie) {
        this.movieMovie = movieMovie;
    }

    public User getUserUser() {
        return userUser;
    }

    public void setUserUser(User userUser) {
        this.userUser = userUser;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
