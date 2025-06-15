import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSelectModule } from '@angular/material/select';
import { AuthService } from '../../services/auth.service';
import { GenreService, Genre } from '../../services/genre.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSelectModule
  ],
  template: `
    <div class="container">
      <div class="register-container">
        <mat-card>
          <mat-card-header>
            <mat-card-title>Register</mat-card-title>
          </mat-card-header>
          
          <mat-card-content>
            <form [formGroup]="registerForm" (ngSubmit)="onSubmit()">
              <div class="form-row">
                <mat-form-field appearance="outline" class="form-field">
                  <mat-label>First Name *</mat-label>
                  <input matInput formControlName="firstName" required>
                  @if (registerForm.get('firstName')?.invalid && registerForm.get('firstName')?.touched) {
                    <mat-error>First name is required</mat-error>
                  }
                </mat-form-field>
                
                <mat-form-field appearance="outline" class="form-field">
                  <mat-label>Last Name *</mat-label>
                  <input matInput formControlName="lastName" required>
                  @if (registerForm.get('lastName')?.invalid && registerForm.get('lastName')?.touched) {
                    <mat-error>Last name is required</mat-error>
                  }
                </mat-form-field>
              </div>
              
              <mat-form-field appearance="outline" class="full-width">
                <mat-label>Username *</mat-label>
                <input matInput formControlName="username" required>
                @if (registerForm.get('username')?.invalid && registerForm.get('username')?.touched) {
                  <mat-error>Username is required</mat-error>
                }
              </mat-form-field>
              
              <mat-form-field appearance="outline" class="full-width">
                <mat-label>Email *</mat-label>
                <input matInput type="email" formControlName="email" required>
                @if (registerForm.get('email')?.invalid && registerForm.get('email')?.touched) {
                  @if (registerForm.get('email')?.errors?.['required']) {
                    <mat-error>Email is required</mat-error>
                  } @else if (registerForm.get('email')?.errors?.['email']) {
                    <mat-error>Please enter a valid email</mat-error>
                  }
                }
              </mat-form-field>
              
              <mat-form-field appearance="outline" class="full-width">
                <mat-label>Password *</mat-label>
                <input matInput type="password" formControlName="password" required>
                @if (registerForm.get('password')?.invalid && registerForm.get('password')?.touched) {
                  @if (registerForm.get('password')?.errors?.['required']) {
                    <mat-error>Password is required</mat-error>
                  } @else if (registerForm.get('password')?.errors?.['minlength']) {
                    <mat-error>Password must be at least 6 characters</mat-error>
                  }
                }
              </mat-form-field>
              
              <mat-form-field appearance="outline" class="full-width">
                <mat-label>Phone Number *</mat-label>
                <input matInput formControlName="phone" required>
                @if (registerForm.get('phone')?.invalid && registerForm.get('phone')?.touched) {
                  <mat-error>Phone number is required</mat-error>
                }
              </mat-form-field>
              
              <mat-form-field appearance="outline" class="full-width">
                <mat-label>Address *</mat-label>
                <input matInput formControlName="address" required>
                @if (registerForm.get('address')?.invalid && registerForm.get('address')?.touched) {
                  <mat-error>Address is required</mat-error>
                }
              </mat-form-field>
              
              <mat-form-field appearance="outline" class="full-width">
                <mat-label>Favorite Movie Genres *</mat-label>
                <mat-select formControlName="favoriteGenres" multiple required>
                  @for (genre of genres; track genre.id) {
                    <mat-option [value]="genre.name">{{ genre.name }}</mat-option>
                  }
                </mat-select>
                @if (registerForm.get('favoriteGenres')?.invalid && registerForm.get('favoriteGenres')?.touched) {
                  <mat-error>Please select at least one genre</mat-error>
                }
              </mat-form-field>
              
              <div class="form-actions">
                <button 
                  mat-raised-button 
                  color="primary" 
                  type="submit" 
                  [disabled]="registerForm.invalid || isLoading"
                  class="full-width">
                  @if (isLoading) {
                    <mat-spinner diameter="20" class="spinner"></mat-spinner>
                  } @else {
                    Register
                  }
                </button>
              </div>
            </form>
          </mat-card-content>
          
          <mat-card-actions>
            <p class="text-center">Already have an account? <a routerLink="/login">Login</a></p>
          </mat-card-actions>
        </mat-card>
      </div>
    </div>
  `,
  styles: `
    .register-container {
      max-width: 600px;
      margin: 40px auto;
    }
    
    .full-width {
      width: 100%;
    }
    
    .form-row {
      display: flex;
      gap: 16px;
    }
    
    .form-field {
      flex: 1;
    }
    
    .form-actions {
      margin-top: 20px;
    }
    
    .spinner {
      display: inline-block;
      margin-right: 8px;
    }
    
    mat-card-actions {
      padding: 16px;
    }
    
    .text-center {
      text-align: center;
    }
  `
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  isLoading = false;
  genres: Genre[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private genreService: GenreService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      phone: ['', Validators.required],
      address: ['', Validators.required],
      favoriteGenres: [[], Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadGenres();
  }

  loadGenres(): void {
    this.genreService.getAllGenres().subscribe({
      next: (genres) => {
        this.genres = genres;
      },
      error: (error) => {
        console.error('Error loading genres', error);
        this.snackBar.open('Failed to load movie genres', 'Close', { duration: 5000 });
      }
    });
  }

  onSubmit(): void {
    if (this.registerForm.invalid || this.isLoading) {
      // Mark all fields as touched to trigger validation messages
      Object.keys(this.registerForm.controls).forEach(field => {
        const control = this.registerForm.get(field);
        control?.markAsTouched();
      });
      return;
    }
    
    this.isLoading = true;
    
    // Convert genre array to comma-separated string for backend
    const formValue = {...this.registerForm.value};
    if (Array.isArray(formValue.favoriteGenres)) {
      formValue.favoriteGenres = formValue.favoriteGenres.join(', ');
    }
    
    this.authService.register(formValue).subscribe({
      next: () => {
        this.snackBar.open('Registration successful! Please log in.', 'Close', { duration: 5000 });
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.isLoading = false;
        this.snackBar.open(
          error.error?.message || 'Registration failed. Please try again.',
          'Close',
          { duration: 5000 }
        );
      }
    });
  }
} 