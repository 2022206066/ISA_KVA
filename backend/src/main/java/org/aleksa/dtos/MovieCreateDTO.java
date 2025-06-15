package org.aleksa.dtos;

import java.time.LocalDate;
import java.util.List;

public class MovieCreateDTO {

    private String name;
    private String description;
    private Integer genreId;
    private Integer directorId;
    private Integer duration;
    private LocalDate releaseDate;
    private List<Integer> actorIds;

    public MovieCreateDTO() {
    }

    public MovieCreateDTO(String name, String description, Integer genreId, Integer directorId, 
                        Integer duration, LocalDate releaseDate, List<Integer> actorIds) {
        this.name = name;
        this.description = description;
        this.genreId = genreId;
        this.directorId = directorId;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.actorIds = actorIds;
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

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
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

    public List<Integer> getActorIds() {
        return actorIds;
    }

    public void setActorIds(List<Integer> actorIds) {
        this.actorIds = actorIds;
    }
}
