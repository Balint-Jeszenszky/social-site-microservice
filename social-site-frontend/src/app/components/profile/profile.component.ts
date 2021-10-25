import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserDetailsDto } from 'src/app/api/auth/models';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    user?: UserDetailsDto;

    constructor(private userService: UserService, private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            const username: string | undefined = params['username'];
            if (username) {
                // TODO find user
            } else {
                this.userService.getCurrentUser().subscribe(user => {
                    this.user = user;
                });
            }
        });
    }
}
