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

import { NewPostDto } from '../models/new-post-dto';
import { PostDto } from '../models/post-dto';

@Injectable({
  providedIn: 'root',
})
export class PostService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation putPostPostId
   */
  static readonly PutPostPostIdPath = '/post/{postId}';

  /**
   * edit post
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `putPostPostId()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putPostPostId$Response(params: {
    postId: number;
    body?: NewPostDto
  }): Observable<StrictHttpResponse<PostDto>> {

    const rb = new RequestBuilder(this.rootUrl, PostService.PutPostPostIdPath, 'put');
    if (params) {
      rb.path('postId', params.postId, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<PostDto>;
      })
    );
  }

  /**
   * edit post
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `putPostPostId$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putPostPostId(params: {
    postId: number;
    body?: NewPostDto
  }): Observable<PostDto> {

    return this.putPostPostId$Response(params).pipe(
      map((r: StrictHttpResponse<PostDto>) => r.body as PostDto)
    );
  }

  /**
   * Path part for operation deletePostPostId
   */
  static readonly DeletePostPostIdPath = '/post/{postId}';

  /**
   * delete post
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deletePostPostId()` instead.
   *
   * This method doesn't expect any request body.
   */
  deletePostPostId$Response(params: {
    postId: number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, PostService.DeletePostPostIdPath, 'delete');
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
   * delete post
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `deletePostPostId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deletePostPostId(params: {
    postId: number;
  }): Observable<void> {

    return this.deletePostPostId$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation postPost
   */
  static readonly PostPostPath = '/post';

  /**
   * Create new post
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postPost$Response(params?: {
    body?: NewPostDto
  }): Observable<StrictHttpResponse<PostDto>> {

    const rb = new RequestBuilder(this.rootUrl, PostService.PostPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<PostDto>;
      })
    );
  }

  /**
   * Create new post
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `postPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postPost(params?: {
    body?: NewPostDto
  }): Observable<PostDto> {

    return this.postPost$Response(params).pipe(
      map((r: StrictHttpResponse<PostDto>) => r.body as PostDto)
    );
  }

  /**
   * Path part for operation getPostAll
   */
  static readonly GetPostAllPath = '/post/all';

  /**
   * Your GET endpoint.
   *
   * Get all friends posts
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getPostAll()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPostAll$Response(params?: {
  }): Observable<StrictHttpResponse<Array<PostDto>>> {

    const rb = new RequestBuilder(this.rootUrl, PostService.GetPostAllPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<PostDto>>;
      })
    );
  }

  /**
   * Your GET endpoint.
   *
   * Get all friends posts
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getPostAll$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPostAll(params?: {
  }): Observable<Array<PostDto>> {

    return this.getPostAll$Response(params).pipe(
      map((r: StrictHttpResponse<Array<PostDto>>) => r.body as Array<PostDto>)
    );
  }

  /**
   * Path part for operation getPostAllUserId
   */
  static readonly GetPostAllUserIdPath = '/post/all/{userId}';

  /**
   * Your GET endpoint.
   *
   * Get posts from user
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getPostAllUserId()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPostAllUserId$Response(params: {
    userId: number;
  }): Observable<StrictHttpResponse<Array<PostDto>>> {

    const rb = new RequestBuilder(this.rootUrl, PostService.GetPostAllUserIdPath, 'get');
    if (params) {
      rb.path('userId', params.userId, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<PostDto>>;
      })
    );
  }

  /**
   * Your GET endpoint.
   *
   * Get posts from user
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getPostAllUserId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPostAllUserId(params: {
    userId: number;
  }): Observable<Array<PostDto>> {

    return this.getPostAllUserId$Response(params).pipe(
      map((r: StrictHttpResponse<Array<PostDto>>) => r.body as Array<PostDto>)
    );
  }

}
