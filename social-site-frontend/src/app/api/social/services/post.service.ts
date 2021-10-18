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
   * Path part for operation getPosts
   */
  static readonly GetPostsPath = '/posts';

  /**
   * Your GET endpoint.
   *
   * Get all friends
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getPosts()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPosts$Response(params?: {
  }): Observable<StrictHttpResponse<Array<PostDto>>> {

    const rb = new RequestBuilder(this.rootUrl, PostService.GetPostsPath, 'get');
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
   * Get all friends
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getPosts$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPosts(params?: {
  }): Observable<Array<PostDto>> {

    return this.getPosts$Response(params).pipe(
      map((r: StrictHttpResponse<Array<PostDto>>) => r.body as Array<PostDto>)
    );
  }

  /**
   * Path part for operation postPosts
   */
  static readonly PostPostsPath = '/posts';

  /**
   * Create new post
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postPosts()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postPosts$Response(params?: {
    body?: NewPostDto
  }): Observable<StrictHttpResponse<PostDto>> {

    const rb = new RequestBuilder(this.rootUrl, PostService.PostPostsPath, 'post');
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
   * To access the full response (for headers, for example), `postPosts$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postPosts(params?: {
    body?: NewPostDto
  }): Observable<PostDto> {

    return this.postPosts$Response(params).pipe(
      map((r: StrictHttpResponse<PostDto>) => r.body as PostDto)
    );
  }

  /**
   * Path part for operation getPostsUserId
   */
  static readonly GetPostsUserIdPath = '/posts/{userId}';

  /**
   * Your GET endpoint.
   *
   * Get posts from user
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getPostsUserId()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPostsUserId$Response(params: {
    userId: number;
  }): Observable<StrictHttpResponse<Array<PostDto>>> {

    const rb = new RequestBuilder(this.rootUrl, PostService.GetPostsUserIdPath, 'get');
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
   * To access the full response (for headers, for example), `getPostsUserId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPostsUserId(params: {
    userId: number;
  }): Observable<Array<PostDto>> {

    return this.getPostsUserId$Response(params).pipe(
      map((r: StrictHttpResponse<Array<PostDto>>) => r.body as Array<PostDto>)
    );
  }

}
