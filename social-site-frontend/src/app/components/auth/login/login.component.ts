import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from 'src/app/api/services';
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
        private userService: UserService
    ) { }

    ngOnInit(): void {
    }

    onLogin() {
        this.loginService.postLogin({
            body: {
                username: this.username, 
                password: this.password 
            } 
        }).subscribe(res => {
            console.log(res);
            this.snackBar.open('success', 'ok');
            this.userService.setCurrentUser(res.userDetails);
        },
        err => {
            console.log(err);
            this.snackBar.open('Wrong credentials', 'ok');
        });
    }

}
