package org.aleksa.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScreeningDTO {
    private Integer id;
    private Integer movieId;
    private String movieName;
    private LocalDate screeningDate;
    private LocalTime screeningTime;
    private BigDecimal price;
    private Integer availableSeats;

    public ScreeningDTO() {
    }

    public ScreeningDTO(Integer id, Integer movieId, String movieName, LocalDate screeningDate, 
                      LocalTime screeningTime, BigDecimal price, Integer availableSeats) {
        this.id = id;
        this.movieId = movieId;
        this.movieName = movieName;
        this.screeningDate = screeningDate;
        this.screeningTime = screeningTime;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(LocalDate screeningDate) {
        this.screeningDate = screeningDate;
    }

    public LocalTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(LocalTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
} 
