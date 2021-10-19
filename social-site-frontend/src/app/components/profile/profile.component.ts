import { Component, OnInit } from '@angular/core';
import { UserDetailsDto } from 'src/app/api/auth/models';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    user?: UserDetailsDto;

    constructor(private userService: UserService) { }

    ngOnInit(): void {
        this.userService.getCurrentUser().subscribe(user => {
            this.user = user;
        });
    }

}
