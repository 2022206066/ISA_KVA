import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ReservationService } from '../../services/reservation.service';
import { AuthService } from '../../services/auth.service';
import { CartItem } from '../../models/reservation.model';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  displayedColumns: string[] = ['movie', 'screening', 'tickets', 'price', 'actions'];
  isProcessing = false;
  
  constructor(
    public reservationService: ReservationService,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}
  
  get cartItems(): CartItem[] {
    return this.reservationService.cartItems();
  }
  
  getCartTotal(): number {
    return this.reservationService.getCartTotal();
  }
  
  removeFromCart(screeningId: number): void {
    this.reservationService.removeFromCart(screeningId);
  }
  
  clearCart(): void {
    this.reservationService.clearCart();
  }
  
  checkout(): void {
    if (this.cartItems.length === 0) {
      return;
    }
    
    const user = this.authService.currentUser();
    if (!user || !user.id) {
      this.snackBar.open('You must be logged in to complete your reservation', 'Close', { duration: 3000 });
      this.router.navigate(['/login'], { queryParams: { returnUrl: '/cart' } });
      return;
    }
    
    this.isProcessing = true;
    
    this.reservationService.createReservationsFromCart(user.id).subscribe({
      next: (reservations) => {
        this.isProcessing = false;
        
        // Check if we received any successful reservations
        if (reservations && reservations.length > 0) {
          this.reservationService.clearCart();
          
          this.snackBar.open(
            `Successfully created ${reservations.length} reservation(s)!`,
            'View Reservations',
            { duration: 5000 }
          ).onAction().subscribe(() => {
            this.router.navigate(['/reservations']);
          });
        } else {
          this.snackBar.open(
            'No reservations were created. Please try again.',
            'Close',
            { duration: 5000 }
          );
        }
      },
      error: (error) => {
        this.isProcessing = false;
        console.error('Error creating reservations', error);
        
        // More specific error message
        let errorMessage = 'Failed to create reservations. Please try again.';
        
        if (error.status === 400) {
          errorMessage = 'Invalid reservation data. Please check your cart.';
        } else if (error.status === 409 || error.error?.message?.includes('seats')) {
          errorMessage = 'Some seats are no longer available. Please refresh and try again.';
        }
        
        this.snackBar.open(
          error.error?.message || errorMessage,
          'Close',
          { duration: 5000 }
        );
      }
    });
  }
  
  formatDate(dateString: string): string {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });
  }
} 