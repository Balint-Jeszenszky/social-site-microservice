import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserManagementService } from 'src/app/api/auth/services';
import { DeleteSocialService } from 'src/app/api/social/services';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-edit-profile',
    templateUrl: './edit-profile.component.html',
    styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
    id: number = 0;
    firstname: string = '';
    lastname: string = '';
    email: string = '';
    oldPassword: string = '';
    newPassword: string = '';
    confirmPassword: string = '';

    constructor(
        private userService: UserService,
        private userManagementService: UserManagementService,
        private router: Router,
        private snackBar: MatSnackBar,
        private deleteSocialService: DeleteSocialService
    ) { }

    ngOnInit(): void {
        this.userService.getCurrentUser().subscribe(user => {
            if (user) {
                this.id = user.id;
                this.firstname = user.firstname;
                this.lastname = user.lastname;
                this.email = user.email;
            }
        });
    }

    onSave() {
        this.userManagementService.putUserId({ 
            id: this.id, 
            body: {
                firstname: this.firstname, 
                lastname: this.lastname,
                email: this.email,
                oldpassword: this.oldPassword,
                newpassword: this.newPassword,
                confirmpassword: this.confirmPassword
            }
        }).subscribe(
            res => {
                this.userService.setCurrentUserDetails(res);
                this.router.navigate(['/profile']);
            },
            err => {
                this.snackBar.open(err.error, 'Ok');
            }
        );
    }

    onDelete() {
        if (confirm('Delete profile?')) {
            this.deleteSocialService.deleteDeleteUserId({userId: this.id}).subscribe(
                res => {
                    this.userManagementService.deleteUserId({ id: this.id }).subscribe(
                        res => {
                            this.userService.logout();
                            this.router.navigate(['/auth']);
                        },
                        err => {
                            this.snackBar.open(err.error, 'Ok');
                        }
                    );
                },
                err => {
                    this.snackBar.open(err.error, 'Ok');
                }
            )
        }
    }
}
