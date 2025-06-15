import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie.model';
import { catchError, of } from 'rxjs';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  template: `
    <div class="container">
      <header class="hero">
        <h1>Welcome to Cinema Ticket System</h1>
        <p>Find the best movies and book your tickets online</p>
        <a routerLink="/movies" mat-raised-button color="primary">Browse All Movies</a>
      </header>

      <section class="top-rated">
        <h2>Top Rated Movies</h2>
        
        @if (topRatedMovies.length > 0) {
          <div class="movie-grid">
            @for (movie of topRatedMovies; track movie.id) {
              <mat-card class="movie-card">
                <mat-card-header>
                  <mat-card-title>{{ movie.name }}</mat-card-title>
                  <mat-card-subtitle>{{ movie.genreGenre.name }}</mat-card-subtitle>
                </mat-card-header>
                <mat-card-content>
                  <p class="rating">
                    <mat-icon>star</mat-icon>
                    {{ movie.averageRating || 'No ratings yet' }}
                  </p>
                  <p class="director">Director: {{ movie.director.name }}</p>
                  <p class="duration">{{ movie.duration }} min</p>
                </mat-card-content>
                <mat-card-actions>
                  <a [routerLink]="['/movies', movie.id]" mat-button color="primary">View Details</a>
                </mat-card-actions>
              </mat-card>
            }
          </div>
        } @else if (loadingTopRated) {
          <div class="loading-container">
            <mat-spinner diameter="50"></mat-spinner>
          </div>
        } @else {
          <p>No top rated movies available.</p>
        }
      </section>

      <section class="latest">
        <h2>Latest Releases</h2>
        
        @if (latestMovies.length > 0) {
          <div class="movie-grid">
            @for (movie of latestMovies; track movie.id) {
              <mat-card class="movie-card">
                <mat-card-header>
                  <mat-card-title>{{ movie.name }}</mat-card-title>
                  <mat-card-subtitle>{{ movie.genreGenre.name }}</mat-card-subtitle>
                </mat-card-header>
                <mat-card-content>
                  <p class="release-date">Released: {{ formatDate(movie.releaseDate) }}</p>
                  <p class="director">Director: {{ movie.director.name }}</p>
                  <p class="duration">{{ movie.duration }} min</p>
                </mat-card-content>
                <mat-card-actions>
                  <a [routerLink]="['/movies', movie.id]" mat-button color="primary">View Details</a>
                </mat-card-actions>
              </mat-card>
            }
          </div>
        } @else if (loadingLatest) {
          <div class="loading-container">
            <mat-spinner diameter="50"></mat-spinner>
          </div>
        } @else {
          <p>No latest movies available.</p>
        }
      </section>
    </div>
  `,
  styles: `
    .hero {
      text-align: center;
      padding: 60px 20px;
      background-color: #f0f0f0;
      border-radius: 8px;
      margin-bottom: 40px;
    }
    
    .hero h1 {
      font-size: 2.5rem;
      margin-bottom: 1rem;
      color: #333;
    }
    
    .hero p {
      font-size: 1.2rem;
      margin-bottom: 2rem;
      color: #666;
    }
    
    h2 {
      font-size: 1.8rem;
      margin-bottom: 1.5rem;
      color: #333;
      border-bottom: 2px solid #3f51b5;
      padding-bottom: 8px;
      display: inline-block;
    }
    
    .movie-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
      gap: 20px;
      margin-bottom: 40px;
    }
    
    .movie-card {
      height: 100%;
      display: flex;
      flex-direction: column;
    }
    
    .movie-card mat-card-content {
      flex-grow: 1;
    }
    
    .rating {
      display: flex;
      align-items: center;
      margin-top: 10px;
    }
    
    .rating mat-icon {
      color: #ffc107;
      margin-right: 5px;
    }
    
    .loading-container {
      display: flex;
      justify-content: center;
      padding: 40px 0;
    }
  `
})
export class HomeComponent implements OnInit {
  topRatedMovies: Movie[] = [];
  latestMovies: Movie[] = [];
  loadingTopRated = true;
  loadingLatest = true;

  constructor(private movieService: MovieService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadTopRatedMovies();
    this.loadLatestMovies();
  }

  loadTopRatedMovies(): void {
    this.loadingTopRated = true;
    this.movieService.getTopRatedMovies()
      .pipe(
        catchError(error => {
          this.handleApiError('Failed to load top rated movies. Please try again later.', error);
          return of([]);
        })
      )
      .subscribe({
        next: (movies) => {
          this.topRatedMovies = movies.slice(0, 4); // Get top 4 movies
        },
        error: () => {}, // Already handled by catchError
        complete: () => {
          this.loadingTopRated = false;
          console.log(this.topRatedMovies.at(0))
        }
      });
  }

  loadLatestMovies(): void {
    this.loadingLatest = true;
    this.movieService.getLatestMovies()
      .pipe(
        catchError(error => {
          this.handleApiError('Failed to load latest movies. Please try again later.', error);
          return of([]);
        })
      )
      .subscribe({
        next: (movies) => {
          this.latestMovies = movies.slice(0, 4); // Get top 4 latest movies
        },
        error: () => {}, // Already handled by catchError
        complete: () => {
          this.loadingLatest = false;
        }
      });
  }

  handleApiError(message: string, error: any): void {
    console.error(message, error);
    this.snackBar.open(message, 'Dismiss', {
      duration: 5000,
      panelClass: 'error-snackbar'
    });
  }

  formatDate(dateString: string): string {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });
  }
} 