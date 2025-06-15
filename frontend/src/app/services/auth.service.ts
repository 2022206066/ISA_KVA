import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap, switchMap } from 'rxjs/operators';
import { User, AuthRequest, UserImpl } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;
  private refreshInProgress = false;
  
  // Signal for current user state
  public currentUser = signal<User | null>(null);
  public isLoggedIn = signal<boolean>(false);

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage(): void {
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
      const userData = JSON.parse(storedUser);
      const user = new UserImpl(userData);
      this.currentUser.set(user);
      this.isLoggedIn.set(true);
    }
  }

  login(username: string, password: string): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/auth/login`, { username, password })
      .pipe(
        tap(userData => {
          const user = new UserImpl(userData);
          this.currentUser.set(user);
          this.isLoggedIn.set(true);
          localStorage.setItem('currentUser', JSON.stringify(user));
        })
      );
  }

  register(userData: AuthRequest): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/auth/register`, userData);
  }

  logout(): void {
    this.currentUser.set(null);
    this.isLoggedIn.set(false);
    localStorage.removeItem('currentUser');
  }

  updateUserProfile(userId: number, userData: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/users/${userId}`, userData)
      .pipe(
        tap(updatedUserData => {
          const currentUser = this.currentUser();
          if (currentUser) {
            const merged = { ...currentUser, ...updatedUserData };
            const user = new UserImpl(merged);
            this.currentUser.set(user);
            localStorage.setItem('currentUser', JSON.stringify(user));
          }
        })
      );
  }

  getToken(): string | null {
    const user = this.currentUser();
    return user?.token || null;
  }
  
  getRefreshToken(): string | null {
    const user = this.currentUser();
    return user?.refreshToken || null;
  }

  isTokenExpired(token: string): boolean {
    if (!token) return true;
    
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expiry = payload.exp * 1000; // Convert to milliseconds
      return Date.now() > expiry;
    } catch (err) {
      return true;
    }
  }
  
  refreshToken(): Observable<any> {
    const refreshToken = this.getRefreshToken();
    
    if (!refreshToken || this.refreshInProgress) {
      return throwError(() => new Error('No refresh token available or refresh in progress'));
    }
    
    this.refreshInProgress = true;
    
    return this.http.post<{accessToken: string}>(`${this.apiUrl}/auth/refresh`, {}, {
      headers: {
        'Authorization': `Bearer ${refreshToken}`
      }
    }).pipe(
      tap(response => {
        const currentUser = this.currentUser();
        if (currentUser) {
          currentUser.token = response.accessToken;
          this.currentUser.set(currentUser);
          localStorage.setItem('currentUser', JSON.stringify(currentUser));
        }
        this.refreshInProgress = false;
      }),
      catchError(error => {
        this.refreshInProgress = false;
        this.logout();
        return throwError(() => error);
      })
    );
  }
  
  getTokenOrRefresh(): Observable<string | null> {
    const token = this.getToken();
    
    if (!token || this.isTokenExpired(token)) {
      return this.refreshToken().pipe(
        switchMap(() => of(this.getToken()))
      );
    }
    
    return of(token);
  }

  isAdmin(): boolean {
    const user = this.currentUser();
    return user instanceof UserImpl ? user.isAdmin() : (user?.role === 'ADMIN' || user?.admin === true || false);
  }
} 