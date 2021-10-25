import { Component, Input, OnInit } from '@angular/core';
import { PostDto } from 'src/app/api/social/models';
import { PostService } from 'src/app/api/social/services';

@Component({
    selector: 'app-feed',
    templateUrl: './feed.component.html',
    styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
    @Input() userId?: number;
    posts?: PostDto[];

    constructor(private postService: PostService) { }

    ngOnInit(): void {
        if (this.userId) {
            this.postService.getPostAllUserId({userId: this.userId}).subscribe(posts => {
                this.posts = posts;
            });
        } else {
            this.postService.getPostAll().subscribe(posts => {
                this.posts = posts;
            });
        }
    }

}
