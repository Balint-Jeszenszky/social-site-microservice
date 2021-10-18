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

import { AccessTokenDto } from '../models/access-token-dto';
import { LoginCredentialsDto } from '../models/login-credentials-dto';
import { LoginDetailsDto } from '../models/login-details-dto';
import { NewTokenDto } from '../models/new-token-dto';
import { RefreshTokenDto } from '../models/refresh-token-dto';
import { UserDetailsDto } from '../models/user-details-dto';

@Injectable({
  providedIn: 'root',
})
export class LoginService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation postLogin
   */
  static readonly PostLoginPath = '/login';

  /**
   * Send login information
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postLogin()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postLogin$Response(params?: {

    /**
     * Login credentials
     */
    body?: LoginCredentialsDto
  }): Observable<StrictHttpResponse<LoginDetailsDto>> {

    const rb = new RequestBuilder(this.rootUrl, LoginService.PostLoginPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<LoginDetailsDto>;
      })
    );
  }

  /**
   * Send login information
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `postLogin$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postLogin(params?: {

    /**
     * Login credentials
     */
    body?: LoginCredentialsDto
  }): Observable<LoginDetailsDto> {

    return this.postLogin$Response(params).pipe(
      map((r: StrictHttpResponse<LoginDetailsDto>) => r.body as LoginDetailsDto)
    );
  }

  /**
   * Path part for operation postLoginRefresh
   */
  static readonly PostLoginRefreshPath = '/login/refresh';

  /**
   * Get new access token with refresh token
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postLoginRefresh()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postLoginRefresh$Response(params?: {
    body?: RefreshTokenDto
  }): Observable<StrictHttpResponse<NewTokenDto>> {

    const rb = new RequestBuilder(this.rootUrl, LoginService.PostLoginRefreshPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<NewTokenDto>;
      })
    );
  }

  /**
   * Get new access token with refresh token
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `postLoginRefresh$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postLoginRefresh(params?: {
    body?: RefreshTokenDto
  }): Observable<NewTokenDto> {

    return this.postLoginRefresh$Response(params).pipe(
      map((r: StrictHttpResponse<NewTokenDto>) => r.body as NewTokenDto)
    );
  }

  /**
   * Path part for operation postLoginDetails
   */
  static readonly PostLoginDetailsPath = '/login/details';

  /**
   * Get user details by token
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postLoginDetails()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postLoginDetails$Response(params?: {
    body?: AccessTokenDto
  }): Observable<StrictHttpResponse<UserDetailsDto>> {

    const rb = new RequestBuilder(this.rootUrl, LoginService.PostLoginDetailsPath, 'post');
    if (params) {
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
   * Get user details by token
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `postLoginDetails$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postLoginDetails(params?: {
    body?: AccessTokenDto
  }): Observable<UserDetailsDto> {

    return this.postLoginDetails$Response(params).pipe(
      map((r: StrictHttpResponse<UserDetailsDto>) => r.body as UserDetailsDto)
    );
  }

}
