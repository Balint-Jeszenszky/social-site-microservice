import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommentDto } from 'src/app/api/social/models/comment-dto';
import { CommentService } from 'src/app/api/social/services/comment.service';

@Component({
    selector: 'app-comment-section',
    templateUrl: './comment-section.component.html',
    styleUrls: ['./comment-section.component.css']
})
export class CommentSectionComponent implements OnInit {
    @Input() postId?: number;
    @Output() commentsChanged: EventEmitter<number> = new EventEmitter<number>();
    comments: CommentDto[] = [];
    comment: string = '';

    constructor(private commentService: CommentService) { }

    ngOnInit(): void {
        if (this.postId) {
            this.commentService.getCommentPostId({postId: this.postId}).subscribe(
                res => {
                    this.comments = res;
                }
            );
        }
    }

    onComment() {
        if (this.postId) {
            this.commentService.postComment({body: {postId: this.postId, text: this.comment}}).subscribe(
                res => {
                    this.comments.push(res);
                    this.comment = '';
                    this.commentsChanged.emit(1);
                }
            );
        }
    }

    commentDeleted(comment: CommentDto) {
        this.comments = this.comments.filter(c => c.id !== comment.id);
        this.commentsChanged.emit(-1);
    }
}
