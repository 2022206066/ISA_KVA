import { Review } from './review.model';
import { Screening } from './screening.model';

export interface Movie {
    id?: number;
    name: string;
    description: string;
    genreGenre: { name:string };
    director: { name:string};
    directorBio?: string;
    duration: number;
    releaseDate: string;
    actors?: string[];
    averageRating?: number;
}

export interface MovieDetails extends Movie {
    reviews?: Review[];
    screenings?: Screening[];
}

export interface MovieCreate {
    name: string;
    description: string;
    genreId: number;
    directorId: number;
    duration: number;
    releaseDate: string;
    actorIds: number[];
} 