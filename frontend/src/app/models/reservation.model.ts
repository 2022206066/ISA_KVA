export interface Reservation {
    id?: number;
    userId: number;
    screeningId: number;
    reservationDate: string;
    numberOfTickets: number;
    totalPrice: number;
    movieName?: string;
    screeningDate?: string;
    screeningTime?: string;
    hallName?: string;
    status?: string; // ACTIVE, CANCELLED, COMPLETED
}

export interface ReservationCreate {
    userId: number;
    screeningId: number;
    numberOfTickets: number;
}

export interface CartItem {
    screeningId: number;
    movieName: string;
    screeningDate: string;
    screeningTime: string;
    hallName: string;
    numberOfTickets: number;
    price: number;
    totalPrice: number;
} 