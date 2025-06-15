export interface Screening {
    id?: number;
    movieId: number;
    movieName?: string;
    screeningDate: string;
    screeningTime: string;
    hallName: string;
    price: number;
    availableSeats?: number;
    formattedDateTime?: string;
}

export interface ScreeningCreate {
    movieId: number;
    screeningDate: string;
    screeningTime: string;
    hallName: string;
    price: number;
    availableSeats: number;
} 