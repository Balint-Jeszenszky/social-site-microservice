import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MediaService {
  private baseUrl: string = '/api/media';

  constructor(private http: HttpClient) { }

  uploadFile(formData: FormData) {
    return this.http.post(`${this.baseUrl}/upload`, formData);
  }
}
