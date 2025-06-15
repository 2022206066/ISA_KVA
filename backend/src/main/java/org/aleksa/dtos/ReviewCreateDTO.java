package org.aleksa.dtos;

public class ReviewCreateDTO {

    private String reviewContent;
    private Boolean isPositive;
    private Integer movieId;
    private Integer userId;
    private Integer rating;

    public ReviewCreateDTO() {
    }

    public ReviewCreateDTO(String reviewContent, Boolean isPositive, Integer movieId, Integer userId, Integer rating) {
        this.reviewContent = reviewContent;
        this.isPositive = isPositive;
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
