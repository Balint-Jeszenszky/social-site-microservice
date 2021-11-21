import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProcessingStatusDto } from './dto/processing-status-dto';
import { UploadResponseDto } from './dto/upload-response-dto';

@Injectable({
    providedIn: 'root'
})
export class MediaService {
    private baseUrl: string = '/api/media';

    constructor(private http: HttpClient) { }

    uploadFile(formData: FormData): Observable<HttpEvent<UploadResponseDto>> {
        return this.http.post<UploadResponseDto>(`${this.baseUrl}/upload`, formData, { reportProgress: true, observe: 'events' });
    }

    getStatus(postId: number): Observable<ProcessingStatusDto> {
        return this.http.get<ProcessingStatusDto>(`${this.baseUrl}/status/${postId}`);
    }

    uploadProfilePicture(userId: number, formData: FormData): Observable<UploadResponseDto> {
        return this.http.post<UploadResponseDto>(`${this.baseUrl}/profilePicture/${userId}`, formData);
    }

    deleteProfilePicture(userId: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/profilePicture/${userId}`);
    }
}
