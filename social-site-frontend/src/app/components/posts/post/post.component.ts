import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { PostDto } from 'src/app/api/social/models';
import { PostService } from 'src/app/api/social/services';
import { MediaStatusEnum } from 'src/app/services/dto/processing-status-dto';
import { MediaService } from 'src/app/services/media.service';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-post',
    templateUrl: './post.component.html',
    styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit, OnDestroy {
    @Input() post?: PostDto;
    editable: boolean = false;
    editing: boolean = false;
    text: string = '';
    processingStatus: number = 0;
    private timerId?: number;

    constructor(
        private userService: UserService,
        private postService: PostService,
        private mediaService: MediaService
    ) { }

    ngOnInit(): void {
        this.userService.getCurrentUser().subscribe(u => {
            this.editable = u?.id === this.post?.user.id;
        });

        if (this.post?.processedMedia === false) {
            console.log('processing');
            this.timerId = setInterval(() => this.updateProcessingStatus(), 500);
        }
    }

    ngOnDestroy(): void {
        if (this.timerId) {
            clearInterval(this.timerId);
        }
    }

    updateProcessingStatus() {
        if (this.post) {
            this.mediaService.getStatus(this.post.id).subscribe(res => {
                this.post!.processedMedia = res.status === MediaStatusEnum[MediaStatusEnum.AVAILABLE];
                this.processingStatus = res.progress;
            });
            if (this.post!.processedMedia) {
                clearInterval(this.timerId);
            }
        }
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

    hasImage() {
        return this.post?.mediaName?.endsWith('.jpg');
    }

    hasVideo() {
        return this.post?.mediaName?.endsWith('.mp4');
    }

}
