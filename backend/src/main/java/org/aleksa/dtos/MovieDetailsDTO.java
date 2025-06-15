package org.aleksa.dtos;

import java.time.LocalDate;
import java.util.List;

public class MovieDetailsDTO {
    private Integer id;
    private String name;
    private String description;
    private String genre;
    private String director;
    private String directorBio;
    private Integer duration;
    private LocalDate releaseDate;
    private List<String> actors;
    private Double averageRating;
    private List<ReviewDTO> reviews;
    private List<ScreeningDTO> screenings;

    public MovieDetailsDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirectorBio() {
        return directorBio;
    }

    public void setDirectorBio(String directorBio) {
        this.directorBio = directorBio;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public List<ScreeningDTO> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<ScreeningDTO> screenings) {
        this.screenings = screenings;
    }
} 
