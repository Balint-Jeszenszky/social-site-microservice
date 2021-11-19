import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { LoginDetailsDto, UserDetailsDto } from '../api/auth/models';
import { LoginService } from '../api/auth/services';

type AccessTokenPayload = {
    sub: string,
    iat: number,
    type: string,
    roles: string[],
    exp: number
}

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private user: ReplaySubject<UserDetailsDto | undefined> = new ReplaySubject();
    private loggedIn: ReplaySubject<boolean> = new ReplaySubject();
    private accessToken?: string;
    private refreshToken?: string;
    private userDetailsKey = 'userDetails';
    private accessTokenPayload?: AccessTokenPayload;

    constructor(private loginService: LoginService) {
        this.loggedIn.next(false);
        const userDetails = localStorage.getItem(this.userDetailsKey);
        if (userDetails) {
            const user: LoginDetailsDto = JSON.parse(userDetails);
            this.setCurrentUser(user);
        }
    }

    setCurrentUser(user: LoginDetailsDto): void {
        this.user.next(user.userDetails);
        this.accessToken = user.accessToken;
        this.refreshToken = user.refreshToken;
        this.accessTokenPayload = this.parseAccessTokenPayload(this.accessToken);
        this.loggedIn.next(true);
        localStorage.setItem(this.userDetailsKey, JSON.stringify(user));
    }

    setCurrentUserDetails(user: UserDetailsDto) {
        this.user.next(user);
        const userDetails = localStorage.getItem(this.userDetailsKey);
        if (userDetails) {
            const parserUser: LoginDetailsDto = JSON.parse(userDetails);
            parserUser.userDetails = user;
            localStorage.setItem(this.userDetailsKey, JSON.stringify(user));
        }
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
        this.accessTokenPayload = undefined;
        this.loggedIn.next(false);
        this.user.next(undefined);
    }

    getAccessToken(): string | undefined {
        return this.accessToken;
    }

    getRefreshToken(): string | undefined {
        return this.refreshToken;
    }

    refreshLogin(): Promise<void> {
        const promise = new Promise<void>((resolve, reject) => {
            if (this.refreshToken) {
                this.loginService.postLoginRefresh({ body: { token: this.refreshToken } }).subscribe(
                    res => {
                        this.refreshToken = res.refreshToken;
                        this.accessToken = res.accessToken;
                        this.accessTokenPayload = this.parseAccessTokenPayload(res.accessToken);
                        resolve();
                    },
                    err => {
                        this.logout();
                        reject();
                    }
                );
            } else {
                reject();
            }
        });

        return promise;
    }

    isAdmin(): boolean {
        return !!this.accessTokenPayload?.roles.includes("ROLE_ADMIN");
    }

    private parseAccessTokenPayload(accessToken: string): AccessTokenPayload {
        return JSON.parse(atob(accessToken.split('.')[1]));
    }
}
