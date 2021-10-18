import { Component, OnInit } from '@angular/core';
import { PostDto } from 'src/app/api/social/models';
import { PostService } from 'src/app/api/social/services';

@Component({
    selector: 'app-feed',
    templateUrl: './feed.component.html',
    styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
    posts: PostDto[] = [];

    constructor(private postService: PostService) { }

    ngOnInit(): void {
        this.postService.getPosts().subscribe(posts => {
            console.log(posts);
            this.posts = posts;
        });
    }

}
