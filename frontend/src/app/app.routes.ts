import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { 
    path: '', 
    loadComponent: () => import('./components/home/home.component').then(c => c.HomeComponent) 
  },
  { 
    path: 'login', 
    loadComponent: () => import('./components/login/login.component').then(c => c.LoginComponent) 
  },
  { 
    path: 'register', 
    loadComponent: () => import('./components/register/register.component').then(c => c.RegisterComponent) 
  },
  { 
    path: 'movies', 
    loadComponent: () => import('./components/movie-list/movie-list.component').then(c => c.MovieListComponent) 
  },
  { 
    path: 'movies/:id', 
    loadComponent: () => import('./components/movie-detail/movie-detail.component').then(c => c.MovieDetailComponent) 
  },
  { 
    path: 'profile', 
    loadComponent: () => import('./components/profile/profile.component').then(c => c.ProfileComponent),
    canActivate: [authGuard]
  },
  { 
    path: 'cart', 
    loadComponent: () => import('./components/cart/cart.component').then(c => c.CartComponent),
    canActivate: [authGuard]
  },
  { 
    path: 'reservations', 
    loadComponent: () => import('./components/reservations/reservations.component').then(c => c.ReservationsComponent),
    canActivate: [authGuard]
  },
  // Redirect all other routes to home for now
  { 
    path: '**', 
    redirectTo: '' 
  }
]; 