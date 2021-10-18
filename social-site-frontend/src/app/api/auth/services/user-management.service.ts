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

import { UpdateUserDto } from '../models/update-user-dto';
import { UserDetailsDto } from '../models/user-details-dto';

@Injectable({
  providedIn: 'root',
})
export class UserManagementService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getUser
   */
  static readonly GetUserPath = '/user/{id}';

  /**
   * Your GET endpoint.
   *
   * Get user details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUser$Response(params: {
    id: number;
  }): Observable<StrictHttpResponse<UserDetailsDto>> {

    const rb = new RequestBuilder(this.rootUrl, UserManagementService.GetUserPath, 'get');
    if (params) {
      rb.path('id', params.id, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<UserDetailsDto>;
      })
    );
  }

  /**
   * Your GET endpoint.
   *
   * Get user details
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUser(params: {
    id: number;
  }): Observable<UserDetailsDto> {

    return this.getUser$Response(params).pipe(
      map((r: StrictHttpResponse<UserDetailsDto>) => r.body as UserDetailsDto)
    );
  }

  /**
   * Path part for operation putUserId
   */
  static readonly PutUserIdPath = '/user/{id}';

  /**
   * Edit user data
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `putUserId()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putUserId$Response(params: {
    id: number;
    body?: UpdateUserDto
  }): Observable<StrictHttpResponse<UserDetailsDto>> {

    const rb = new RequestBuilder(this.rootUrl, UserManagementService.PutUserIdPath, 'put');
    if (params) {
      rb.path('id', params.id, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<UserDetailsDto>;
      })
    );
  }

  /**
   * Edit user data
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `putUserId$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putUserId(params: {
    id: number;
    body?: UpdateUserDto
  }): Observable<UserDetailsDto> {

    return this.putUserId$Response(params).pipe(
      map((r: StrictHttpResponse<UserDetailsDto>) => r.body as UserDetailsDto)
    );
  }

  /**
   * Path part for operation deleteUserId
   */
  static readonly DeleteUserIdPath = '/user/{id}';

  /**
   * Delete user
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteUserId()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteUserId$Response(params: {
    id: number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, UserManagementService.DeleteUserIdPath, 'delete');
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
   * Delete user
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `deleteUserId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteUserId(params: {
    id: number;
  }): Observable<void> {

    return this.deleteUserId$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
