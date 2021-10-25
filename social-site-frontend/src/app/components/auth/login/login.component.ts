import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/api/auth/services';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    username: string = '';
    password: string = '';

    constructor(
        private loginService: LoginService,
        private snackBar: MatSnackBar,
        private userService: UserService,
        private router: Router
    ) { }

    ngOnInit(): void {
    }

    onLogin() {
        this.loginService.postLogin({
            body: {
                username: this.username, 
                password: this.password 
            } 
        }).subscribe(
            res => {
                this.userService.setCurrentUser(res);
                this.router.navigate(['/']);
            },
            err => {
                console.log(err);
                this.snackBar.open('Wrong credentials', 'Ok');
            }
        );
    }

}
