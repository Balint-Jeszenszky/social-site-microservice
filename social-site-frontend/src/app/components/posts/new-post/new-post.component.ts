import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/api/social/services';

@Component({
    selector: 'app-new-post',
    templateUrl: './new-post.component.html',
    styleUrls: ['./new-post.component.css']
})
export class NewPostComponent implements OnInit {
    text: string = '';

    constructor(private postService: PostService) { }

    ngOnInit(): void {
    }

    post() {
        this.postService.postPosts({body:{text: this.text}}).subscribe(res => {
            this.text = '';
        });
    }

}
