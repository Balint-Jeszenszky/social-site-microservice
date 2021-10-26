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

import { UserDetailsDto } from '../models/user-details-dto';

@Injectable({
  providedIn: 'root',
})
export class FriendsService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation postFriendId
   */
  static readonly PostFriendIdPath = '/friend/{id}';

  /**
   * send or accept friend request
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postFriendId()` instead.
   *
   * This method doesn't expect any request body.
   */
  postFriendId$Response(params: {
    id: number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, FriendsService.PostFriendIdPath, 'post');
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
   * send or accept friend request
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `postFriendId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  postFriendId(params: {
    id: number;
  }): Observable<void> {

    return this.postFriendId$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation deleteFriendId
   */
  static readonly DeleteFriendIdPath = '/friend/{id}';

  /**
   * delete friend or friend request
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteFriendId()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteFriendId$Response(params: {
    id: number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, FriendsService.DeleteFriendIdPath, 'delete');
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
   * delete friend or friend request
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `deleteFriendId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteFriendId(params: {
    id: number;
  }): Observable<void> {

    return this.deleteFriendId$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation getFriendListUserId
   */
  static readonly GetFriendListUserIdPath = '/friend/list/{userId}';

  /**
   * Your GET endpoint.
   *
   * list friends of a user
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFriendListUserId()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFriendListUserId$Response(params: {
    userId: number;
  }): Observable<StrictHttpResponse<Array<UserDetailsDto>>> {

    const rb = new RequestBuilder(this.rootUrl, FriendsService.GetFriendListUserIdPath, 'get');
    if (params) {
      rb.path('userId', params.userId, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<UserDetailsDto>>;
      })
    );
  }

  /**
   * Your GET endpoint.
   *
   * list friends of a user
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getFriendListUserId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFriendListUserId(params: {
    userId: number;
  }): Observable<Array<UserDetailsDto>> {

    return this.getFriendListUserId$Response(params).pipe(
      map((r: StrictHttpResponse<Array<UserDetailsDto>>) => r.body as Array<UserDetailsDto>)
    );
  }

  /**
   * Path part for operation getFriendRequests
   */
  static readonly GetFriendRequestsPath = '/friend/requests';

  /**
   * Your GET endpoint.
   *
   * List of pending requests
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFriendRequests()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFriendRequests$Response(params?: {
  }): Observable<StrictHttpResponse<Array<UserDetailsDto>>> {

    const rb = new RequestBuilder(this.rootUrl, FriendsService.GetFriendRequestsPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<UserDetailsDto>>;
      })
    );
  }

  /**
   * Your GET endpoint.
   *
   * List of pending requests
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getFriendRequests$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFriendRequests(params?: {
  }): Observable<Array<UserDetailsDto>> {

    return this.getFriendRequests$Response(params).pipe(
      map((r: StrictHttpResponse<Array<UserDetailsDto>>) => r.body as Array<UserDetailsDto>)
    );
  }

}
