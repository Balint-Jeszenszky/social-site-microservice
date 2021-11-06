import { Component, Input, OnInit } from '@angular/core';
import { PostDto } from 'src/app/api/social/models';
import { PostService } from 'src/app/api/social/services';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-post',
    templateUrl: './post.component.html',
    styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
    @Input() post?: PostDto;
    editable: boolean = false;
    editing: boolean = false;
    text: string = '';
    mediaUrl?: string;

    constructor(private userService: UserService, private postService: PostService) { }

    ngOnInit(): void {
        this.userService.getCurrentUser().subscribe(u => {
            this.editable = u?.id === this.post?.user.id;
        })
    }

    editPost() {
        this.editing = true;
        this.text = this.post!.text;
    }

    savePost() {
        this.postService.putPostPostId({postId: this.post!.id, body: { text: this.text, media: false }}).subscribe(res => {
            this.editing = false;
            this.post!.text = this.text;
        });
    }

    cancelEditPost() {
        this.editing = false;
    }

    deletePost() {
        if (confirm('Are you sure?')) {
            this.postService.deletePostPostId({postId: this.post!.id}).subscribe(res => {
                this.post = undefined;
            });
        }
    }

}
