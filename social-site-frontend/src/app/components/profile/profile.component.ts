import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicUserDetailsDto, UserDetailsDto } from 'src/app/api/auth/models';
import { UserManagementService } from 'src/app/api/auth/services';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    user?: UserDetailsDto | PublicUserDetailsDto;
    personalProfile: boolean = false;
    userNotFound: boolean = false;

    constructor(
        private userService: UserService,
        private userManagementService: UserManagementService,
        private route: ActivatedRoute
    ) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            const username: string | undefined = params['username'];
            if (username) {
                this.userManagementService.getPublicUserFindUsername({ username }).subscribe(
                    res => {
                        this.user = res;
                    },
                    err => {
                        this.userNotFound = true;
                    }
                );
            } else {
                this.userService.getCurrentUser().subscribe(user => {
                    this.user = user;
                    this.personalProfile = true;
                });
            }
        });
    }
}
