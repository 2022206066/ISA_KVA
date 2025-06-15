export interface User {
    id?: number;
    username: string;
    email: string;
    firstName?: string;
    lastName?: string;
    phone?: string;
    address?: string;
    favoriteGenres?: string;
    token?: string;
    refreshToken?: string;
    role?: string;
    admin?: boolean;
    
    isAdmin?(): boolean;
}

export interface AuthRequest {
    username: string;
    email: string;
    password: string;
    firstName?: string;
    lastName?: string;
    phone?: string;
    address?: string;
    favoriteGenres?: string;
}

// Extend the User interface with implementation
export class UserImpl implements User {
    id?: number;
    username: string;
    email: string;
    firstName?: string;
    lastName?: string;
    phone?: string;
    address?: string;
    favoriteGenres?: string;
    token?: string;
    refreshToken?: string;
    role?: string;
    admin?: boolean;
    
    constructor(user: User) {
        this.id = user.id;
        this.username = user.username;
        this.email = user.email;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.phone = user.phone;
        this.address = user.address;
        this.favoriteGenres = user.favoriteGenres;
        this.token = user.token;
        this.refreshToken = user.refreshToken;
        this.role = user.role;
        this.admin = user.admin;
    }
    
    isAdmin(): boolean {
        return this.admin === true || this.role === 'ADMIN';
    }
} 