import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie, MovieDetails, MovieCreate } from '../models/movie.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private apiUrl = `${environment.apiUrl}/movies`;

  constructor(private http: HttpClient) { }

  getMovies(searchCriteria?: { name?: string, genre?: string }): Observable<Movie[]> {
    let params = new HttpParams();
    
    if (searchCriteria?.name) {
      params = params.set('name', searchCriteria.name);
    }
    
    if (searchCriteria?.genre) {
      params = params.set('genre', searchCriteria.genre);
    }

    return this.http.get<Movie[]>(`${this.apiUrl}/all`, { params });
  }

  getMovieById(id: number): Observable<MovieDetails> {
    return this.http.get<MovieDetails>(`${this.apiUrl}/${id}`);
  }

  createMovie(movie: MovieCreate): Observable<Movie> {
    return this.http.post<Movie>(this.apiUrl, movie);
  }

  updateMovie(id: number, movie: Partial<Movie>): Observable<Movie> {
    return this.http.put<Movie>(`${this.apiUrl}/${id}`, movie);
  }

  deleteMovie(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getTopRatedMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.apiUrl}/top-rated`);
  }

  getLatestMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.apiUrl}/latest`);
  }

  getMoviesByGenre(genre: string): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.apiUrl}/genre/${genre}`);
  }
} 