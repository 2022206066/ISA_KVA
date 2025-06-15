import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin } from 'rxjs';
import { Reservation, ReservationCreate, CartItem } from '../models/reservation.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = `http://localhost:8080/reservation`;
  
  // Cart items stored in signal
  public cartItems = signal<CartItem[]>([]);
  
  constructor(private http: HttpClient) {
    this.loadCartFromStorage();
  }
  
  private loadCartFromStorage(): void {
    const storedCart = localStorage.getItem('cart');
    if (storedCart) {
      this.cartItems.set(JSON.parse(storedCart));
    }
  }
  
  saveCartToStorage(): void {
    localStorage.setItem('cart', JSON.stringify(this.cartItems()));
  }
  
  addToCart(item: CartItem): void {
    const currentItems = this.cartItems();
    // Check if item already exists in cart
    const existingItemIndex = currentItems.findIndex(i => i.screeningId === item.screeningId);
    
    if (existingItemIndex >= 0) {
      // Update existing item
      const updatedItems = [...currentItems];
      const existingItem = updatedItems[existingItemIndex];
      updatedItems[existingItemIndex] = {
        ...existingItem,
        numberOfTickets: existingItem.numberOfTickets + item.numberOfTickets,
        totalPrice: (existingItem.numberOfTickets + item.numberOfTickets) * existingItem.price
      };
      this.cartItems.set(updatedItems);
    } else {
      // Add new item
      this.cartItems.set([...currentItems, item]);
    }
    
    this.saveCartToStorage();
  }
  
  removeFromCart(screeningId: number): void {
    const currentItems = this.cartItems();
    const updatedItems = currentItems.filter(item => item.screeningId !== screeningId);
    this.cartItems.set(updatedItems);
    this.saveCartToStorage();
  }
  
  clearCart(): void {
    this.cartItems.set([]);
    localStorage.removeItem('cart');
  }
  
  getCartTotal(): number {
    return this.cartItems().reduce((total, item) => total + item.totalPrice, 0);
  }
  
  createReservation(reservation: ReservationCreate): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, reservation);
  }
  
  createReservationsFromCart(userId: number): Observable<Reservation[]> {
    // Map cart items to reservation requests
    const reservationRequests = this.cartItems().map(item => ({
      userId,
      screeningId: item.screeningId,
      seatNumber: String(item.numberOfTickets)
    }));
    
    // Use forkJoin to process all requests in parallel and return when all complete
    return forkJoin(
      reservationRequests.map(reservation => 
        this.http.post<Reservation>(`${this.apiUrl}/create`, reservation)
      )
    );
  }
  
  getUserReservations(userId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/user/${userId}`);
  }
  
  cancelReservation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
} 