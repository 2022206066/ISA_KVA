import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Screening, ScreeningCreate } from '../models/screening.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ScreeningService {
  private apiUrl = `${environment.apiUrl}/screening`;

  constructor(private http: HttpClient) { }

  getScreenings(
    movieId?: number, 
    date?: string,
    minTime?: string,
    maxTime?: string
  ): Observable<Screening[]> {
    let params = new HttpParams();
    
    if (movieId) {
      params = params.set('movieId', movieId.toString());
    }
    
    if (date) {
      params = params.set('date', date);
    }
    
    if (minTime) {
      params = params.set('minTime', minTime);
    }
    
    if (maxTime) {
      params = params.set('maxTime', maxTime);
    }

    return this.http.get<Screening[]>(`${this.apiUrl}/all`, { params });
  }

  getScreeningById(id: number): Observable<Screening> {
    return this.http.get<Screening>(`${this.apiUrl}/${id}`);
  }

  createScreening(screening: ScreeningCreate): Observable<Screening> {
    return this.http.post<Screening>(`${this.apiUrl}/add`, screening);
  }

  updateScreening(id: number, screening: Partial<Screening>): Observable<Screening> {
    return this.http.put<Screening>(`${this.apiUrl}/update/${id}`, screening);
  }

  deleteScreening(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

  getScreeningsByMovie(movieId: number): Observable<Screening[]> {
    return this.http.get<Screening[]>(`${this.apiUrl}/movie/${movieId}`);
  }

  getAvailableSeats(screeningId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/${screeningId}/availableSeats`);
  }
} 