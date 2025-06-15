package org.aleksa.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationDTO {
    private Integer id;
    private Integer userId;
    private Integer screeningId;
    private String movieName;
    private LocalDate screeningDate;
    private LocalTime screeningTime;
    private String status;
    private Byte rating;
    private LocalDateTime reservationDate;
    private String seatNumber;
    private BigDecimal price;

    public ReservationDTO() {
    }

    public ReservationDTO(Integer id, Integer userId, Integer screeningId, String movieName, 
                          LocalDate screeningDate, LocalTime screeningTime, String status, 
                          Byte rating, LocalDateTime reservationDate, String seatNumber, BigDecimal price) {
        this.id = id;
        this.userId = userId;
        this.screeningId = screeningId;
        this.movieName = movieName;
        this.screeningDate = screeningDate;
        this.screeningTime = screeningTime;
        this.status = status;
        this.rating = rating;
        this.reservationDate = reservationDate;
        this.seatNumber = seatNumber;
        this.price = price;
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

    public Integer getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Integer screeningId) {
        this.screeningId = screeningId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
} 
