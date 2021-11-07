/* tslint:disable */
/* eslint-disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';

import { MediaStatusDto } from '../models/media-status-dto';

@Injectable({
  providedIn: 'root',
})
export class MediaService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getMediaId
   */
  static readonly GetMediaIdPath = '/media/{id}';

  /**
   * Get url with key for media
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getMediaId()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMediaId$Response(params: {
    id: number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, MediaService.GetMediaIdPath, 'get');
    if (params) {
      rb.path('id', params.id, {});
    }

    return this.http.request(rb.build({
      responseType: 'text',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return (r as HttpResponse<any>).clone({ body: undefined }) as StrictHttpResponse<void>;
      })
    );
  }

  /**
   * Get url with key for media
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getMediaId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMediaId(params: {
    id: number;
  }): Observable<void> {

    return this.getMediaId$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation putMedia
   */
  static readonly PutMediaPath = '/media/{id}';

  /**
   * Notify of status change
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `putMedia()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putMedia$Response(params: {
    id: number;
    body?: MediaStatusDto
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, MediaService.PutMediaPath, 'put');
    if (params) {
      rb.path('id', params.id, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'text',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return (r as HttpResponse<any>).clone({ body: undefined }) as StrictHttpResponse<void>;
      })
    );
  }

  /**
   * Notify of status change
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `putMedia$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putMedia(params: {
    id: number;
    body?: MediaStatusDto
  }): Observable<void> {

    return this.putMedia$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
