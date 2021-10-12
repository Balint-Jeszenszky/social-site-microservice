import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { UserDetailsDto } from '../api/models';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private user: ReplaySubject<UserDetailsDto | undefined> = new ReplaySubject();

    constructor() { }

    setCurrentUser(user?: UserDetailsDto): void {
        this.user.next(user);
        console.log('current user:', user);
    }

    getCurrentUser(): Observable<UserDetailsDto | undefined> {
        return this.user.asObservable();
    }

}
