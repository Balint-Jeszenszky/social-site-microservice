import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
    loggedIn: boolean = false;

    constructor(private userService: UserService, private router: Router) { }

    ngOnInit(): void {
        this.userService.isLoggedIn().subscribe(loggedIn => this.loggedIn = loggedIn);
    }

    onLogout() {
        this.userService.logout();
        this.router.navigate(['/auth'])
    }

}
