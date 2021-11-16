import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { PasswordResetService } from 'src/app/api/auth/services';

@Component({
    selector: 'app-password-reset',
    templateUrl: './password-reset.component.html',
    styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {
    newPassword: string = '';
    confirmPassword: string = '';
    key?: string = '';

    constructor(
        private passwordResetService: PasswordResetService,
        private route: ActivatedRoute,
        private router: Router,
        private snackBar: MatSnackBar
    ) { }

    ngOnInit(): void {
        this.route.queryParams.subscribe(params => {
            this.key = params['key'];
            if (!this.key) {
                this.router.navigate(['/auth']);
            }
        });
    }

    onReset() {
        if (this.key) {
            this.passwordResetService.putForgotPassword({body: {key: this.key, newPassword: this.newPassword, confirmPassword: this.confirmPassword}}).subscribe(
                res => {
                    this.snackBar.open('Successful reset', 'Ok');
                    this.router.navigate(['/auth']);
                },
                err => {
                    this.snackBar.open(err.error, 'Ok');
                }
            );
        }
    }
}
