import { Component, OnInit } from '@angular/core';
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
                    res => {
                        this.selectedFile = undefined;
                        this.filename = '';
                        console.log(res);
                    },
                    err => {
                        console.log(err);
                    }
                );
            }

            this.text = '';
        });
    }

    onFileSelected(event: Event) {
        const element = event.currentTarget as HTMLInputElement;
        const fileList: FileList | null = element.files;
        if (fileList) {
            this.selectedFile = fileList[0];
            this.filename = fileList[0].name;
        }
    }

}
