package org.aleksa.dtos;

import java.time.LocalDateTime;

public class ReviewDTO {

    private Integer id;
    private String reviewContent;
    private Boolean isPositive;
    private Integer movieId;
    private String movieName;
    private Integer userId;
    private String username;
    private LocalDateTime reviewDate;
    private Integer rating;

    public ReviewDTO() {
    }

    public ReviewDTO(Integer id, String reviewContent, Boolean isPositive, Integer movieId, String movieName, 
                   Integer userId, String username, LocalDateTime reviewDate, Integer rating) {
        this.id = id;
        this.reviewContent = reviewContent;
        this.isPositive = isPositive;
        this.movieId = movieId;
        this.movieName = movieName;
        this.userId = userId;
        this.username = username;
        this.reviewDate = reviewDate;
        this.rating = rating;
    }

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

    public Boolean getIsPositive() {
        return isPositive;
    }

    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
