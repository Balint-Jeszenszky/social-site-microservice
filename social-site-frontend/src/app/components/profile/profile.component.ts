import { Component, OnInit } from '@angular/core';
import { MatBottomSheet, MatBottomSheetRef } from '@angular/material/bottom-sheet';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { PublicUserDetailsDto, UserDetailsDto } from 'src/app/api/auth/models';
import { UserManagementService } from 'src/app/api/auth/services';
import { DeleteSocialService } from 'src/app/api/social/services';
import { UserService } from 'src/app/services/user.service';
import { ProfilePictureEditSheetComponent } from './profile-picture-edit-sheet/profile-picture-edit-sheet.component';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    user?: UserDetailsDto | PublicUserDetailsDto;
    personalProfile: boolean = false;
    userNotFound: boolean = false;
    deletable: boolean = false;
    profilePictureChangeSheet?: MatBottomSheetRef;

    constructor(
        private userService: UserService,
        private userManagementService: UserManagementService,
        private route: ActivatedRoute,
        private deleteSocialService: DeleteSocialService,
        private router: Router,
        private snackBar: MatSnackBar,
        private bottomSheet: MatBottomSheet
    ) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            const username: string | undefined = params['username'];
            if (username) {
                this.userManagementService.getPublicUserFindUsername({ username }).subscribe(
                    res => {
                        this.user = res;
                        this.deletable = this.userService.isAdmin();
                    },
                    err => {
                        this.userNotFound = true;
                    }
                );
            } else {
                this.userService.getCurrentUser().subscribe(user => {
                    this.user = user;
                    this.personalProfile = true;
                    this.profilePictureChangeSheet?.dismiss();
                });
            }
        });
    }

    deleteUser() {
        if (this.user) {
            const userId = this.user.id;
            if (confirm('Delete profile?')) {
                this.deleteSocialService.deleteDeleteUserId({userId}).subscribe(
                    res => {
                        this.userManagementService.deleteUserId({ id: userId }).subscribe(
                            res => {
                                this.router.navigate(['/']);
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

    onPictureClick() {
        if (this.personalProfile && this.user) {
            this.profilePictureChangeSheet = this.bottomSheet.open(ProfilePictureEditSheetComponent, {data: {userId: this.user.id}});
            this.profilePictureChangeSheet.afterDismissed().subscribe(() => this.profilePictureChangeSheet = undefined);
        }
    }
}
