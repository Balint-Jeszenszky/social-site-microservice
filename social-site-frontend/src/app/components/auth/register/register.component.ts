import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RegisterService } from 'src/app/api/services';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
    firstName: string = '';
    lastName: string = '';
    email: string = '';
    username: string = '';
    password: string = '';
    confirmPassword: string = '';

    constructor(private registerService: RegisterService, private snackBar: MatSnackBar) { }

    ngOnInit(): void {
    }

    onRegister() {
        this.registerService.postRegister({
            body: {
                firstname: this.firstName,
                lastname: this.lastName,
                email: this.email,
                username: this.username,
                password: this.password,
                confirmPassword: this.confirmPassword
            }
        }).subscribe(res => {
            console.log(res);
            this.snackBar.open('Succesful registration!', 'Ok');
            this.firstName = '';
            this.lastName = '';
            this.email = '';
            this.username = '';
            this.password = '';
            this.confirmPassword = '';
        },
        err => {
            console.log(err);
            this.snackBar.open(err.error, 'Ok');
        });
    }

}
