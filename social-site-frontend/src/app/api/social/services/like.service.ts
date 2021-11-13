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


@Injectable({
  providedIn: 'root',
})
export class LikeService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation postLikePostId
   */
  static readonly PostLikePostIdPath = '/like/{postId}';

  /**
   * like a post
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postLikePostId()` instead.
   *
   * This method doesn't expect any request body.
   */
  postLikePostId$Response(params: {
    postId: number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, LikeService.PostLikePostIdPath, 'post');
    if (params) {
      rb.path('postId', params.postId, {});
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
   * like a post
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `postLikePostId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  postLikePostId(params: {
    postId: number;
  }): Observable<void> {

    return this.postLikePostId$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation deleteLikePostId
   */
  static readonly DeleteLikePostIdPath = '/like/{postId}';

  /**
   * remove like from post
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteLikePostId()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteLikePostId$Response(params: {
    postId: number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, LikeService.DeleteLikePostIdPath, 'delete');
    if (params) {
      rb.path('postId', params.postId, {});
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
   * remove like from post
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `deleteLikePostId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteLikePostId(params: {
    postId: number;
  }): Observable<void> {

    return this.deleteLikePostId$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
