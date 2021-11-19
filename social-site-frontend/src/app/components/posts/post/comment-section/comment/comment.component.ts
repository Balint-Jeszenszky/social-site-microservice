import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommentDto } from 'src/app/api/social/models/comment-dto';
import { CommentService } from 'src/app/api/social/services/comment.service';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-comment',
    templateUrl: './comment.component.html',
    styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {
    @Input() comment?: CommentDto;
    @Output() commentDeleted: EventEmitter<CommentDto> = new EventEmitter<CommentDto>();
    editable: boolean = false;
    deletable: boolean = false;
    editing: boolean = false;
    text: string = '';

    constructor(private userService: UserService, private commentService: CommentService) { }

    ngOnInit(): void {
        this.userService.getCurrentUser().subscribe(u => {
            this.editable = u?.id === this.comment?.user.id && this.comment?.user.id !== undefined;
            this.deletable = this.editable || this.userService.isAdmin();
        });
    }

    onEdit() {
        this.editing = true;
        if (this.comment) {
            this.text = this.comment.text;
        }
    }

    onDelete() {
        if (this.comment) {
            if (confirm('Are you sure?')) {
                this.commentService.deleteCommentCommentId({commentId: this.comment.id}).subscribe(
                    res => {
                        this.commentDeleted.emit(this.comment);
                    }
                );
            }
        }
    }

    onSave() {
        if (this.comment) {
            this.commentService.putCommentCommentId({commentId: this.comment.id, body: {postId: this.comment.postId, text: this.text}}).subscribe(
                res => {
                    this.comment = res;
                    this.editing = false;
                }
            );
        }
    }

    onCancelEdit() {
        this.editing = false;
    }
}
