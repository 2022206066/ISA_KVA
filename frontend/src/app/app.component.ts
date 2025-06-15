import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatBadgeModule } from '@angular/material/badge';
import { AuthService } from './services/auth.service';
import { ReservationService } from './services/reservation.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatBadgeModule
  ],
  templateUrl: './app.component.html',
  styles: `
    .app-container {
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }
    
    .spacer {
      flex: 1 1 auto;
    }
    
    .logo {
      text-decoration: none;
      color: white;
      font-size: 1.5rem;
      font-weight: 500;
    }
    
    nav a {
      margin-left: 8px;
    }
    
    .active {
      background-color: rgba(255, 255, 255, 0.2);
    }
    
    .content {
      flex: 1;
      padding: 20px 0;
    }
    
    .footer {
      background-color: #f5f5f5;
      padding: 20px 0;
      text-align: center;
      margin-top: auto;
    }
  `
})
export class AppComponent {
  currentYear = new Date().getFullYear();
  
  constructor(
    public authService: AuthService,
    private reservationService: ReservationService
  ) {}
  
  getUserName(): string {
    const user = this.authService.currentUser();
    return user ? user.username : 'User';
  }
  
  getCartItemCount(): number {
    return this.reservationService.cartItems().length;
  }
  
  logout(): void {
    this.authService.logout();
    this.reservationService.clearCart();
    window.location.href = '/';
  }
} 