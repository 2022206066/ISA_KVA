package org.aleksa.dtos;

import java.time.LocalDateTime;

public class WatchDTO {
    private Integer id;
    private Integer userId;
    private String username;
    private Integer movieId;
    private String movieName;
    private LocalDateTime watchedDate;

    public WatchDTO() {
    }

    public WatchDTO(Integer id, Integer userId, String username, Integer movieId, String movieName, LocalDateTime watchedDate) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.movieId = movieId;
        this.movieName = movieName;
        this.watchedDate = watchedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDateTime getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(LocalDateTime watchedDate) {
        this.watchedDate = watchedDate;
    }
}
