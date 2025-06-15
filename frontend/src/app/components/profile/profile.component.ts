import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule
  ],
  template: `
    <div class="container">
      <h1>User Profile</h1>
      
      <mat-card *ngIf="user">
        <mat-card-header>
          <mat-card-title>{{ user.username }}</mat-card-title>
          <mat-card-subtitle>{{ user.email }}</mat-card-subtitle>
        </mat-card-header>
        <mat-card-content>
          <p>User ID: {{ user.id }}</p>
          <p>Role: {{ isAdmin() ? 'Administrator' : 'User' }}</p>
        </mat-card-content>
      </mat-card>
      
      <div *ngIf="!user">
        <p>You are not logged in.</p>
      </div>
    </div>
  `,
  styles: `
    .container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
    }
    
    mat-card {
      margin-top: 20px;
    }
  `
})
export class ProfileComponent {
  constructor(private authService: AuthService) {}
  
  get user(): User | null {
    return this.authService.currentUser();
  }
  
  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
} 