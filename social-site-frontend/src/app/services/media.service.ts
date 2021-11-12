import { HttpClient } from '@angular/common/http';
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

    uploadFile(formData: FormData): Observable<UploadResponseDto> {
        return this.http.post<UploadResponseDto>(`${this.baseUrl}/upload`, formData);
    }

    getStatus(postId: number, needFilename: boolean): Observable<ProcessingStatusDto> {
        return this.http.get<ProcessingStatusDto>(`${this.baseUrl}/status/${postId}`, { params: { needFilename } });
    }
}
