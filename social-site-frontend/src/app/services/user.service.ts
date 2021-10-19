import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { LoginDetailsDto, UserDetailsDto } from '../api/auth/models';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private user: ReplaySubject<UserDetailsDto | undefined> = new ReplaySubject();
    private loggedIn: ReplaySubject<boolean> = new ReplaySubject();
    private accessToken?: string;
    private refreshToken?: string;
    private userDetailsKey = 'userDetails';

    constructor() {
        this.loggedIn.next(false);
        const userDetails = localStorage.getItem(this.userDetailsKey);
        if (userDetails) {
            const user: LoginDetailsDto = JSON.parse(userDetails);
            this.setCurrentUserDetails(user);
        }
    }

    setCurrentUserDetails(user: LoginDetailsDto): void {
        this.user.next(user.userDetails);
        this.accessToken = user.accessToken;
        this.refreshToken = user.refreshToken;
        console.log('accessToken:', this.accessToken);
        console.log('refreshToken:', this.refreshToken);
        this.loggedIn.next(true);
        localStorage.setItem(this.userDetailsKey, JSON.stringify(user));
    }

    getCurrentUser(): Observable<UserDetailsDto | undefined> {
        return this.user.asObservable();
    }

    isLoggedIn(): Observable<boolean> {
        return this.loggedIn.asObservable();
    }

    logout(): void {
        localStorage.removeItem(this.userDetailsKey);
        this.accessToken = undefined;
        this.refreshToken = undefined;
        this.loggedIn.next(false);
        this.user.next(undefined);
    }

    getAccessToken(): string | undefined {
        return this.accessToken;
    }
}
