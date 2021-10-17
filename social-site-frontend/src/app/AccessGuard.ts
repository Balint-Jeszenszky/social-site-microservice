import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate } from "@angular/router";
import { Observable } from "rxjs";
import { UserService } from "./services/user.service";

@Injectable()
export class AccessGuard implements CanActivate {
    private loggedIn: boolean = false;

    constructor(private userService: UserService) { 
        this.userService.isLoggedIn().subscribe(loggedIn => this.loggedIn = loggedIn);
    }

    canActivate(route: ActivatedRouteSnapshot): boolean {
        if (route.data.requiresLogin) {
            return this.loggedIn;
        }
        if (route.data.requiresLogout) {
            return !this.loggedIn;
        }
        return true;
    }
}