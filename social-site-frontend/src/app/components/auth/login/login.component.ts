import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService, PasswordResetService } from 'src/app/api/auth/services';
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
        private router: Router,
        private passwordResetService: PasswordResetService
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

    onResetPassword() {
        const email = prompt('Enter your email:');

        if (email) {
            this.passwordResetService.postForgotPassword({body: {email}}).subscribe(
                res => {
                    this.snackBar.open('Email sent', 'Ok');
                },
                err => {
                    this.snackBar.open(err.error, 'Ok');
                }
            );
        }
    }
}
