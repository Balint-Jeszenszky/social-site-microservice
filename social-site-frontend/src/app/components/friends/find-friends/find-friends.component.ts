import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PublicUserDetailsDto, UserDetailsDto } from 'src/app/api/auth/models';
import { UserManagementService } from 'src/app/api/auth/services';
import { FriendsService } from 'src/app/api/social/services';

@Component({
    selector: 'app-find-friends',
    templateUrl: './find-friends.component.html',
    styleUrls: ['./find-friends.component.css']
})
export class FindFriendsComponent implements OnInit {
    users: PublicUserDetailsDto[] = [];
    query: string = '';

    constructor(
        private userManagementService: UserManagementService,
        private friendService: FriendsService,
        private snackBar: MatSnackBar
    ) { }

    ngOnInit(): void {
    }

    search() {
        if (this.query.length > 2) {
            this.userManagementService.getPublicUserSearchQuery({ query: this.query }).subscribe(res => {
                this.users = res;
                console.log(res)
            });
        } else {
            this.users = [];
        }
    }

    addUser(id: number) {
        this.friendService.postFriendId({ id }).subscribe(
            res => {
                this.snackBar.open('Request sent', 'Ok');
            },
            err =>{
                this.snackBar.open(err.error, 'Ok');
            }
        );
    }

}
