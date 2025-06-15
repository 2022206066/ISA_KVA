export interface Review {
    id?: number;
    reviewContent: string;
    isPositive: boolean;
    movieId: number;
    movieName?: string;
    userId: number;
    username?: string;
    reviewDate?: string;
    rating: number;
}

export interface ReviewCreate {
    reviewContent: string;
    isPositive: boolean;
    movieId: number;
    userId: number;
    rating: number;
} 