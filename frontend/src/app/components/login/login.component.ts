import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule
  ],
  template: `
    <div class="container">
      <div class="login-container">
        <mat-card>
          <mat-card-header>
            <mat-card-title>Login</mat-card-title>
          </mat-card-header>
          
          <mat-card-content>
            <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
              <mat-form-field appearance="outline" class="full-width">
                <mat-label>Username</mat-label>
                <input matInput formControlName="username" required>
                @if (loginForm.get('username')?.invalid && loginForm.get('username')?.touched) {
                  <mat-error>Username is required</mat-error>
                }
              </mat-form-field>
              
              <mat-form-field appearance="outline" class="full-width">
                <mat-label>Password</mat-label>
                <input matInput type="password" formControlName="password" required>
                @if (loginForm.get('password')?.invalid && loginForm.get('password')?.touched) {
                  <mat-error>Password is required</mat-error>
                }
              </mat-form-field>
              
              <div class="form-actions">
                <button 
                  mat-raised-button 
                  color="primary" 
                  type="submit" 
                  [disabled]="loginForm.invalid || isLoading"
                  class="full-width">
                  @if (isLoading) {
                    <mat-spinner diameter="20" class="spinner"></mat-spinner>
                  } @else {
                    Login
                  }
                </button>
              </div>
            </form>
          </mat-card-content>
          
          <mat-card-actions>
            <p class="text-center">Don't have an account? <a routerLink="/register">Register</a></p>
          </mat-card-actions>
        </mat-card>
      </div>
    </div>
  `,
  styles: `
    .login-container {
      max-width: 400px;
      margin: 40px auto;
    }
    
    .full-width {
      width: 100%;
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
  `
})
export class LoginComponent {
  loginForm: FormGroup;
  isLoading = false;
  returnUrl: string = '/';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    
    // Get return URL from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  onSubmit(): void {
    if (this.loginForm.invalid || this.isLoading) {
      return;
    }
    
    this.isLoading = true;
    const { username, password } = this.loginForm.value;
    
    this.authService.login(username, password).subscribe({
      next: () => {
        this.router.navigate([this.returnUrl]);
      },
      error: (error) => {
        this.isLoading = false;
        this.snackBar.open(
          error.error?.message || 'Login failed. Please check your credentials.',
          'Close',
          { duration: 5000 }
        );
      }
    });
  }
} 