import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserDetailsDto } from 'src/app/api/social/models';
import { FriendsService } from 'src/app/api/social/services';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-list-friends',
    templateUrl: './list-friends.component.html',
    styleUrls: ['./list-friends.component.css']
})
export class ListFriendsComponent implements OnInit {
    friends: UserDetailsDto[] = [];

    constructor(
        private userService: UserService, 
        private friendService: FriendsService,
        private snackBar: MatSnackBar
    ) { }

    ngOnInit(): void {
        this.userService.getCurrentUser().subscribe(user => {
            this.friendService.getFriendListUserId({ userId: user!.id}).subscribe(res => {
                this.friends = res;
            });
        });
    }

    delteFriend(id: number) {
        if (confirm('Are you sure?')) {
            this.friendService.deleteFriendId({id}).subscribe(
                res => {
                    this.friends = this.friends.filter(f => f.id !== id);
                },
                err => {
                    this.snackBar.open(err.error, 'Ok');
                }
            );
        }
    }
}
