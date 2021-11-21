import { Component, Inject, OnInit } from '@angular/core';
import { MAT_BOTTOM_SHEET_DATA } from '@angular/material/bottom-sheet';
import { UserManagementService } from 'src/app/api/auth/services';
import { MediaService } from 'src/app/services/media.service';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-profile-picture-edit-sheet',
    templateUrl: './profile-picture-edit-sheet.component.html',
    styleUrls: ['./profile-picture-edit-sheet.component.css']
})
export class ProfilePictureEditSheetComponent implements OnInit {

    constructor(
        @Inject(MAT_BOTTOM_SHEET_DATA) public data: {userId: number},
        private userManagementService: UserManagementService,
        private mediaService: MediaService,
        private userService: UserService
    ) { }

    ngOnInit(): void {
    }

    deletePicture() {
        this.mediaService.deleteProfilePicture(this.data.userId).subscribe(res => {
            this.userManagementService.patchUserId({id: this.data.userId, body: {name: undefined}}).subscribe(res => {
                this.userService.profilePictureChange(undefined);
            });
        });
    }

    onFileSelected(event: Event) {
        const element = event.currentTarget as HTMLInputElement;
        const fileList: FileList | null = element.files;

        if (fileList) {
            const acceptedFiles = ['jpeg', 'jpg', 'png'];

            if (!acceptedFiles.includes(fileList[0].name.split('.').pop()?.toLowerCase() || '')) {
                alert(`Accepted file types: ${acceptedFiles.join(', ')}`);
                return;
            }

            if (fileList[0].size > 1024 ** 2 * 2) {
                alert('Max file size: 2 MB');
                return;
            }
            
            const formData = new FormData();
            formData.append('profilePicture', fileList[0]);

            this.mediaService.uploadProfilePicture(this.data.userId, formData).subscribe(res => {
                const name = res.name.split('.')[0];
                this.userManagementService.patchUserId({id: this.data.userId, body: {name}}).subscribe(res => {
                    this.userService.profilePictureChange(name);
                });
            });
        }
    }
}
