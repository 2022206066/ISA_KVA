import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ReservationService } from '../../services/reservation.service';
import { AuthService } from '../../services/auth.service';
import { Reservation } from '../../models/reservation.model';

@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  templateUrl: './reservations.component.html',
  styles: `
    .reservations-container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 20px;
    }
    
    .loading-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 50px;
    }
    
    .loading-container p {
      margin-top: 20px;
      color: #666;
    }
    
    .no-reservations {
      text-align: center;
      margin-top: 50px;
      color: #666;
    }
    
    .no-reservations mat-icon {
      font-size: 48px;
      height: 48px;
      width: 48px;
      margin-bottom: 16px;
    }
    
    .reservation-table {
      width: 100%;
      margin-top: 20px;
    }
    
    .status-chip {
      border-radius: 16px;
      padding: 4px 12px;
      font-size: 12px;
      font-weight: 500;
    }
    
    .status-reserved {
      background-color: #e3f2fd;
      color: #1976d2;
    }
    
    .status-watched {
      background-color: #e8f5e9;
      color: #43a047;
    }
    
    .status-canceled {
      background-color: #ffebee;
      color: #e53935;
    }
  `
})
export class ReservationsComponent implements OnInit {
  reservations: Reservation[] = [];
  isLoading = true;
  displayedColumns: string[] = ['movie', 'screening', 'seats', 'price', 'status', 'actions'];
  
  constructor(
    private reservationService: ReservationService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}
  
  ngOnInit(): void {
    this.loadUserReservations();
  }
  
  loadUserReservations(): void {
    const user = this.authService.currentUser();
    if (!user || !user.id) {
      this.isLoading = false;
      return;
    }
    
    this.reservationService.getUserReservations(user.id).subscribe({
      next: (reservations) => {
        this.reservations = reservations;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading reservations', error);
        this.isLoading = false;
        this.snackBar.open('Failed to load reservations', 'Close', { duration: 5000 });
      }
    });
  }
  
  cancelReservation(id: number): void {
    if (!confirm('Are you sure you want to cancel this reservation?')) {
      return;
    }
    
    this.reservationService.cancelReservation(id).subscribe({
      next: () => {
        this.snackBar.open('Reservation canceled successfully', 'Close', { duration: 3000 });
        this.loadUserReservations();
      },
      error: (error) => {
        console.error('Error canceling reservation', error);
        this.snackBar.open('Failed to cancel reservation', 'Close', { duration: 5000 });
      }
    });
  }
  
  formatDate(dateString: string): string {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });
  }
} 