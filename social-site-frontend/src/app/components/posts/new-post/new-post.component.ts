import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { PostDto } from 'src/app/api/social/models';
import { PostService } from 'src/app/api/social/services';
import { MediaService } from 'src/app/services/media.service';

@Component({
    selector: 'app-new-post',
    templateUrl: './new-post.component.html',
    styleUrls: ['./new-post.component.css']
})
export class NewPostComponent implements OnInit {
    text: string = '';
    filename: string = '';
    selectedFile?: File;
    @Output() postCreated: EventEmitter<PostDto> = new EventEmitter<PostDto>();

    constructor(private postService: PostService, private mediaService: MediaService) { }

    ngOnInit(): void {
    }

    post() {
        this.postService.postPost({body:{text: this.text, media: !!this.selectedFile}}).subscribe(res => {
            if (this.selectedFile) {
                const formData = new FormData();
                formData.append('mediaUpload', this.selectedFile);
                formData.append('postId', res.id.toString());
                this.mediaService.uploadFile(formData).subscribe(
                    resMedia => {
                        this.selectedFile = undefined;
                        this.filename = '';
                        this.text = '';
                        this.postCreated.emit(res);
                        console.log(resMedia);
                    },
                    err => {
                        console.log(err);
                    }
                );
            } else {
                this.text = '';
                this.postCreated.emit(res);
            }
        });
    }

    onFileSelected(event: Event) {
        const element = event.currentTarget as HTMLInputElement;
        const fileList: FileList | null = element.files;
        if (fileList) {
            const acceptedFiles = ['jpeg', 'jpg', 'png', 'mp4', 'mov', 'doc', 'docx', 'ppt', 'pptx', 'xls', 'xlsx', 'pdf', 'txt'];
            if (!acceptedFiles.includes(fileList[0].name.split('.').pop() || '')) {
                alert(`Accepted file types: ${acceptedFiles.join(', ')}`);
                return;
            }
            if (fileList[0].size > 1024**2*256) {
                alert('Max file size: 256 MB');
                return;
            }
            this.selectedFile = fileList[0];
            this.filename = fileList[0].name;
        }
    }

}
