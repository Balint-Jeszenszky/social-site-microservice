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

import { CommentDto } from '../models/comment-dto';
import { NewCommentDto } from '../models/new-comment-dto';

@Injectable({
  providedIn: 'root',
})
export class CommentService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getCommentPostId
   */
  static readonly GetCommentPostIdPath = '/comment/post/{postId}';

  /**
   * Get comments for post
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getCommentPostId()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCommentPostId$Response(params: {
    postId: number;
  }): Observable<StrictHttpResponse<Array<CommentDto>>> {

    const rb = new RequestBuilder(this.rootUrl, CommentService.GetCommentPostIdPath, 'get');
    if (params) {
      rb.path('postId', params.postId, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<CommentDto>>;
      })
    );
  }

  /**
   * Get comments for post
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getCommentPostId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCommentPostId(params: {
    postId: number;
  }): Observable<Array<CommentDto>> {

    return this.getCommentPostId$Response(params).pipe(
      map((r: StrictHttpResponse<Array<CommentDto>>) => r.body as Array<CommentDto>)
    );
  }

  /**
   * Path part for operation postComment
   */
  static readonly PostCommentPath = '/comment';

  /**
   * Create a comment for a post
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postComment()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postComment$Response(params?: {
    body?: NewCommentDto
  }): Observable<StrictHttpResponse<CommentDto>> {

    const rb = new RequestBuilder(this.rootUrl, CommentService.PostCommentPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<CommentDto>;
      })
    );
  }

  /**
   * Create a comment for a post
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `postComment$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postComment(params?: {
    body?: NewCommentDto
  }): Observable<CommentDto> {

    return this.postComment$Response(params).pipe(
      map((r: StrictHttpResponse<CommentDto>) => r.body as CommentDto)
    );
  }

  /**
   * Path part for operation putCommentCommentId
   */
  static readonly PutCommentCommentIdPath = '/comment/{commentId}';

  /**
   * Edit comment
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `putCommentCommentId()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putCommentCommentId$Response(params: {
    commentId: number;
    body?: NewCommentDto
  }): Observable<StrictHttpResponse<CommentDto>> {

    const rb = new RequestBuilder(this.rootUrl, CommentService.PutCommentCommentIdPath, 'put');
    if (params) {
      rb.path('commentId', params.commentId, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<CommentDto>;
      })
    );
  }

  /**
   * Edit comment
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `putCommentCommentId$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putCommentCommentId(params: {
    commentId: number;
    body?: NewCommentDto
  }): Observable<CommentDto> {

    return this.putCommentCommentId$Response(params).pipe(
      map((r: StrictHttpResponse<CommentDto>) => r.body as CommentDto)
    );
  }

  /**
   * Path part for operation deleteCommentCommentId
   */
  static readonly DeleteCommentCommentIdPath = '/comment/{commentId}';

  /**
   * Delete comment
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteCommentCommentId()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteCommentCommentId$Response(params: {
    commentId: number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, CommentService.DeleteCommentCommentIdPath, 'delete');
    if (params) {
      rb.path('commentId', params.commentId, {});
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
   * Delete comment
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `deleteCommentCommentId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteCommentCommentId(params: {
    commentId: number;
  }): Observable<void> {

    return this.deleteCommentCommentId$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
