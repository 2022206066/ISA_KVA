import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie.model';

@Component({
  selector: 'app-movie-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatChipsModule
  ],
  template: `
    <div class="container">
      <h1>All Movies</h1>
      
      <div class="filters">
        <mat-form-field appearance="outline">
          <mat-label>Search Movies</mat-label>
          <input matInput [formControl]="searchControl" placeholder="Enter movie name...">
          <mat-icon matSuffix>search</mat-icon>
        </mat-form-field>
        
        <mat-form-field appearance="outline">
          <mat-label>Filter by Genre</mat-label>
          <mat-select [formControl]="genreControl">
            <mat-option value="">All Genres</mat-option>
            @for (genre of genres; track genre) {
              <mat-option [value]="genre">{{ genre }}</mat-option>
            }
          </mat-select>
        </mat-form-field>
      </div>
      
      @if (loading) {
        <div class="loading-container">
          <mat-spinner diameter="50"></mat-spinner>
        </div>
      } @else if (filteredMovies.length === 0) {
        <div class="no-results">
          <mat-icon>movie_off</mat-icon>
          <p>No movies found matching your criteria.</p>
        </div>
      } @else {
        <div class="movie-grid">
          @for (movie of filteredMovies; track movie.id) {
            <mat-card class="movie-card">
              <mat-card-header>
                <mat-card-title>{{ movie.name }}</mat-card-title>
                <mat-card-subtitle>
                  <span class="genre-chip">{{ movie.genreGenre.name }}</span>
                </mat-card-subtitle>
              </mat-card-header>
              
              <mat-card-content>
                <p class="truncate-text">{{ movie.description }}</p>
                <div class="movie-details">
                  <div class="detail-item">
                    <mat-icon>person</mat-icon>
                    <span>{{ movie.director.name }}</span>
                  </div>
                  <div class="detail-item">
                    <mat-icon>schedule</mat-icon>
                    <span>{{ movie.duration }} min</span>
                  </div>
                  <div class="detail-item">
                    <mat-icon>star</mat-icon>
                    <span>{{ movie.averageRating || 'Not rated' }}</span>
                  </div>
                </div>
              </mat-card-content>
              
              <mat-card-actions>
                <a [routerLink]="['/movies', movie.id]" mat-button color="primary">View Details</a>
              </mat-card-actions>
            </mat-card>
          }
        </div>
      }
    </div>
  `,
  styles: `
    .filters {
      display: flex;
      gap: 16px;
      margin-bottom: 24px;
    }
    
    mat-form-field {
      flex: 1;
    }
    
    .movie-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 24px;
    }
    
    .movie-card {
      height: 100%;
      display: flex;
      flex-direction: column;
      transition: transform 0.3s, box-shadow 0.3s;
    }
    
    .movie-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    }
    
    .movie-card mat-card-content {
      flex-grow: 1;
    }
    
    .truncate-text {
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      overflow: hidden;
      margin-bottom: 16px;
    }
    
    .movie-details {
      margin-top: 16px;
    }
    
    .detail-item {
      display: flex;
      align-items: center;
      margin-bottom: 8px;
    }
    
    .detail-item mat-icon {
      margin-right: 8px;
      font-size: 18px;
      height: 18px;
      width: 18px;
      color: #666;
    }
    
    .genre-chip {
      background-color: #f0f0f0;
      padding: 4px 8px;
      border-radius: 16px;
      font-size: 12px;
    }
    
    .loading-container, .no-results {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 60px 0;
    }
    
    .no-results mat-icon {
      font-size: 48px;
      height: 48px;
      width: 48px;
      margin-bottom: 16px;
      color: #999;
    }
    
    .no-results p {
      font-size: 18px;
      color: #666;
    }
  `
})
export class MovieListComponent implements OnInit {
  movies: Movie[] = [];
  filteredMovies: Movie[] = [];
  genres: string[] = [];
  loading = true;
  
  searchControl = new FormControl('');
  genreControl = new FormControl('');

  constructor(private movieService: MovieService) {}

  ngOnInit(): void {
    this.loadMovies();
    
    // Subscribe to search changes
    this.searchControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(value => {
      this.filterMovies();
    });
    
    // Subscribe to genre filter changes
    this.genreControl.valueChanges.subscribe(() => {
      this.filterMovies();
    });
  }

  loadMovies(): void {
    this.loading = true;
    this.movieService.getMovies().subscribe({
      next: (movies) => {
        this.movies = movies;
        this.filteredMovies = [...movies];
        this.extractGenres();
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading movies', error);
        this.loading = false;
      }
    });
  }
  
  extractGenres(): void {
    const uniqueGenres = new Set<string>();
    this.movies.forEach(movie => {
      if (movie.genreGenre) {
        uniqueGenres.add(movie.genreGenre.name);
      }
    });
    this.genres = Array.from(uniqueGenres).sort();
  }
  
  filterMovies(): void {
    const searchTerm = this.searchControl.value?.toLowerCase() || '';
    const genreFilter = this.genreControl.value || '';
    
    this.filteredMovies = this.movies.filter(movie => {
      const matchesSearch = !searchTerm || movie.name.toLowerCase().includes(searchTerm);
      const matchesGenre = !genreFilter || movie.genreGenre.name === genreFilter;
      
      return matchesSearch && matchesGenre;
    });
  }
} 