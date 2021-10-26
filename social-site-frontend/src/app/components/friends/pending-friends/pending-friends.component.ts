import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserDetailsDto } from 'src/app/api/social/models';
import { FriendsService } from 'src/app/api/social/services';

@Component({
    selector: 'app-pending-friends',
    templateUrl: './pending-friends.component.html',
    styleUrls: ['./pending-friends.component.css']
})
export class PendingFriendsComponent implements OnInit {
    users: UserDetailsDto[] = [];

    constructor(private friendService: FriendsService, private snackBar: MatSnackBar) { }

    ngOnInit(): void {
        this.friendService.getFriendRequests().subscribe(
            res => {
                this.users = res;
            }
        );
    }

    acceptFriend(id: number) {
        this.friendService.postFriendId({ id }).subscribe(
            res => {
                this.users = this.users.filter(u => u.id !== id);
                this.snackBar.open('Friend request accepted', 'Ok');
            }
        );
    }

}
