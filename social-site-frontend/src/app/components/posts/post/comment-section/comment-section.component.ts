import { Component, Input, OnInit } from '@angular/core';
import { CommentDto } from 'src/app/api/social/models/comment-dto';
import { CommentService } from 'src/app/api/social/services/comment.service';

@Component({
    selector: 'app-comment-section',
    templateUrl: './comment-section.component.html',
    styleUrls: ['./comment-section.component.css']
})
export class CommentSectionComponent implements OnInit {
    @Input() postId?: number;
    comments: CommentDto[] = [];

    constructor(private commentService: CommentService) { }

    ngOnInit(): void {
    }

}
