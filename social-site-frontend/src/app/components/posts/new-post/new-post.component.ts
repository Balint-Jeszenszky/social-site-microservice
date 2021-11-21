import { HttpEventType, HttpResponse } from '@angular/common/http';
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
    @Output() postCreated: EventEmitter<PostDto> = new EventEmitter<PostDto>();
    text: string = '';
    filename: string = '';
    selectedFile?: File;
    posting: boolean = false;
    progress: number = 0;

    constructor(private postService: PostService, private mediaService: MediaService) { }

    ngOnInit(): void {
    }

    post() {
        this.posting = true;
        this.postService.postPost({ body: { text: this.text, media: !!this.selectedFile } }).subscribe(
            res => {
                this.posting = !!this.selectedFile;
                if (this.selectedFile) {
                    const formData = new FormData();
                    formData.append('mediaUpload', this.selectedFile);
                    formData.append('postId', res.id.toString());
                    this.mediaService.uploadFile(formData).subscribe(
                        event => {
                            if (event.type === HttpEventType.UploadProgress && event.total) {
                                this.progress = 100 * event.loaded / event.total;
                            } else if (event instanceof HttpResponse) {
                                this.selectedFile = undefined;
                                this.filename = '';
                                this.text = '';
                                this.postCreated.emit(res);
                                this.posting = false;
                            }
                        },
                        err => {
                            console.log(err);
                            this.posting = false;
                        }
                    );
                } else {
                    this.text = '';
                    this.postCreated.emit(res);
                }
            },
            err => {
                this.posting = false;
            }
        );
    }

    onFileSelected(event: Event) {
        const element = event.currentTarget as HTMLInputElement;
        const fileList: FileList | null = element.files;
        if (fileList) {
            const acceptedFiles = ['jpeg', 'jpg', 'png', 'mp4', 'mov', 'doc', 'docx', 'ppt', 'pptx', 'xls', 'xlsx', 'pdf', 'txt'];
            if (!acceptedFiles.includes(fileList[0].name.split('.').pop()?.toLowerCase() || '')) {
                alert(`Accepted file types: ${acceptedFiles.join(', ')}`);
                return;
            }
            if (fileList[0].size > 1024 ** 2 * 256) {
                alert('Max file size: 256 MB');
                return;
            }
            this.selectedFile = fileList[0];
            this.filename = fileList[0].name;
        }
    }

}
