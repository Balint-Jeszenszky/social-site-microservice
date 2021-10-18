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

    constructor() {
        this.loggedIn.next(false);
    }

    setCurrentUserDetails(user: LoginDetailsDto): void {
        this.user.next(user.userDetails);
        this.accessToken = user.accessToken;
        this.refreshToken = user.refreshToken;
        console.log(this.accessToken);
        this.loggedIn.next(true);
    }

    getCurrentUser(): Observable<UserDetailsDto | undefined> {
        return this.user.asObservable();
    }

    isLoggedIn(): Observable<boolean> {
        return this.loggedIn.asObservable();
    }

    logout(): void {
        this.loggedIn.next(false);
        this.user.next(undefined);
    }
}
