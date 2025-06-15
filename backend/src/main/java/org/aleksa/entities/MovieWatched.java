package org.aleksa.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_watched")
public class MovieWatched {
    @Id
    @Column(name = "movie_watched_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User userUser;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "movie_movie_id", nullable = false)
    private Movie movieMovie;

    @Column(name = "watched_date", nullable = false)
    private LocalDateTime watchedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserUser() {
        return userUser;
    }

    public void setUserUser(User userUser) {
        this.userUser = userUser;
    }

    public Movie getMovieMovie() {
        return movieMovie;
    }

    public void setMovieMovie(Movie movieMovie) {
        this.movieMovie = movieMovie;
    }

    public LocalDateTime getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(LocalDateTime watchedDate) {
        this.watchedDate = watchedDate;
    }
}
