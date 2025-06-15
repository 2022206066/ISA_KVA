import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatChipsModule } from '@angular/material/chips';
import { MatRadioModule } from '@angular/material/radio';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MovieService } from '../../services/movie.service';
import { ScreeningService } from '../../services/screening.service';
import { ReviewService } from '../../services/review.service';
import { ReservationService } from '../../services/reservation.service';
import { AuthService } from '../../services/auth.service';
import { MovieDetails } from '../../models/movie.model';
import { Screening } from '../../models/screening.model';
import { Review, ReviewCreate } from '../../models/review.model';
import { CartItem } from '../../models/reservation.model';

@Component({
  selector: 'app-movie-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonModule,
    MatTabsModule,
    MatIconModule,
    MatDividerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatChipsModule,
    MatRadioModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {
  movie: MovieDetails | null = null;
  screenings: Screening[] = [];
  filteredScreenings: Screening[] = [];
  reviews: Review[] = [];
  loading = true;
  isSubmittingReview = false;
  
  dateControl = new FormControl();
  ticketForm: FormGroup;
  reviewForm: FormGroup;
  
  currentUrl = '';
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private movieService: MovieService,
    private screeningService: ScreeningService,
    private reviewService: ReviewService,
    private reservationService: ReservationService,
    public authService: AuthService,
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar
  ) {
    this.ticketForm = this.formBuilder.group({
      numberOfTickets: [1, [Validators.required, Validators.min(1)]]
    });
    
    this.reviewForm = this.formBuilder.group({
      reviewContent: ['', Validators.required],
      rating: [5, Validators.required],
      isPositive: [true, Validators.required]
    });
    
    this.currentUrl = this.router.url;
  }
  
  ngOnInit(): void {
    this.dateControl.valueChanges.subscribe(date => {
      this.filterScreeningsByDate(date);
    });
    
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id) {
        this.loadMovieDetails(id);
      } else {
        this.loading = false;
      }
    });
  }
  
  loadMovieDetails(id: number): void {
    this.loading = true;
    this.movieService.getMovieById(id).subscribe({
      next: (movie) => {
        this.movie = movie;
        this.loadScreenings(id);
        this.loadReviews(id);
      },
      error: (error) => {
        console.error('Error loading movie details', error);
        this.loading = false;
        this.movie = null;
      }
    });
  }
  
  loadScreenings(movieId: number): void {
    this.screeningService.getScreeningsByMovie(movieId).subscribe({
      next: (screenings: Screening[]) => {
        this.screenings = screenings;
        this.filteredScreenings = screenings;
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Error loading screenings', error);
        this.screenings = [];
        this.filteredScreenings = [];
        this.loading = false;
      }
    });
  }
  
  loadReviews(movieId: number): void {
    this.reviewService.getReviewsByMovie(movieId).subscribe({
      next: (reviews: Review[]) => {
        this.reviews = reviews;
      },
      error: (error: any) => {
        console.error('Error loading reviews', error);
        this.reviews = [];
      }
    });
  }
  
  filterScreeningsByDate(date: Date | null): void {
    if (!date) {
      this.filteredScreenings = this.screenings;
      return;
    }
    
    const dateStr = this.formatDateForFilter(date);
    this.filteredScreenings = this.screenings.filter(s => 
      s.screeningDate === dateStr
    );
  }
  
  clearDateFilter(): void {
    this.dateControl.setValue(null);
    this.filteredScreenings = this.screenings;
  }
  
  formatDateForFilter(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    
    return `${year}-${month}-${day}`;
  }
  
  addToCart(screening: Screening): void {
    if (!this.authService.isLoggedIn()) {
      this.snackBar.open('You must be logged in to add items to cart', 'Log In', { duration: 3000 })
        .onAction().subscribe(() => {
          this.router.navigate(['/login'], { queryParams: { returnUrl: this.currentUrl } });
        });
      return;
    }
    
    if (this.ticketForm.invalid) return;
    
    const numberOfTickets = this.ticketForm.get('numberOfTickets')?.value;
    
    if (numberOfTickets > (screening.availableSeats || 0)) {
      this.snackBar.open(`Only ${screening.availableSeats} seats available`, 'Close', { duration: 3000 });
      return;
    }
    
    if (!this.movie) return;
    
    const cartItem: CartItem = {
      screeningId: screening.id ?? 0,
      movieName: this.movie.name,
      screeningDate: screening.screeningDate || '',
      screeningTime: screening.screeningTime || '',
      hallName: screening.hallName || '',
      price: screening.price || 0,
      numberOfTickets: numberOfTickets || 0,
      totalPrice: (screening.price || 0) * (numberOfTickets || 0)
    };
    
    this.reservationService.addToCart(cartItem);
    
    this.ticketForm.get('numberOfTickets')?.setValue(1);
    
    this.snackBar.open(
      `Added ${numberOfTickets} ticket(s) to cart`, 
      'View Cart', 
      { duration: 3000 }
    ).onAction().subscribe(() => {
      this.router.navigate(['/cart']);
    });
  }
  
  submitReview(): void {
    if (this.reviewForm.invalid || !this.movie || !this.authService.isLoggedIn()) {
      return;
    }
    
    const user = this.authService.currentUser();
    if (!user) {
      this.snackBar.open('You must be logged in to leave a review', 'Log In', { duration: 3000 })
        .onAction().subscribe(() => {
          this.router.navigate(['/login'], { queryParams: { returnUrl: this.currentUrl } });
        });
      return;
    }
    
    this.isSubmittingReview = true;
    
    const reviewData: ReviewCreate = {
      movieId: this.movie.id ?? 0,
      userId: user.id ?? 0,
      reviewContent: this.reviewForm.get('reviewContent')?.value,
      isPositive: this.reviewForm.get('isPositive')?.value,
      rating: this.reviewForm.get('rating')?.value
    };
    
    this.reviewService.createReview(reviewData).subscribe({
      next: (newReview) => {
        this.isSubmittingReview = false;
        
        // Add username from current user since the backend doesn't return it
        const reviewWithUsername: Review = {
          ...newReview,
          username: user.username
        };
        
        this.reviews = [reviewWithUsername, ...this.reviews];
        
        // Update movie rating
        if (this.movie) {
          const totalRatings = this.reviews.length;
          const sumRatings = this.reviews.reduce((sum, r) => sum + r.rating, 0);
          this.movie.averageRating = sumRatings / totalRatings;
        }
        
        this.reviewForm.reset({
          reviewContent: '',
          rating: 5,
          isPositive: true
        });
        
        this.snackBar.open('Your review has been submitted', 'Close', { duration: 3000 });
      },
      error: (error) => {
        this.isSubmittingReview = false;
        console.error('Error submitting review', error);
        this.snackBar.open(
          error.error?.message || 'Failed to submit review. Please try again.',
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
  
  createRatingArray(count: number): number[] {
    return new Array(count);
  }
} 