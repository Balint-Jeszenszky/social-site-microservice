import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
}
