import { Component, OnInit } from '@angular/core';
import { PostsDto } from 'src/app/api/social/models';
import { PostService } from 'src/app/api/social/services';

@Component({
    selector: 'app-feed',
    templateUrl: './feed.component.html',
    styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
    posts: PostsDto = [];

    constructor(private postService: PostService) { }

    ngOnInit(): void {
        this.postService.getPosts().subscribe(posts => {
            this.posts = posts;
        });
    }

}
