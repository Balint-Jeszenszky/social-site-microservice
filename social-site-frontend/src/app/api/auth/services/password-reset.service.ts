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

import { ForgotPasswordDto } from '../models/forgot-password-dto';
import { PasswordResetDto } from '../models/password-reset-dto';

@Injectable({
  providedIn: 'root',
})
export class PasswordResetService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation putForgotPassword
   */
  static readonly PutForgotPasswordPath = '/passwordReset';

  /**
   * Reset the password with the key
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `putForgotPassword()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putForgotPassword$Response(params?: {
    body?: PasswordResetDto
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, PasswordResetService.PutForgotPasswordPath, 'put');
    if (params) {
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
   * Reset the password with the key
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `putForgotPassword$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  putForgotPassword(params?: {
    body?: PasswordResetDto
  }): Observable<void> {

    return this.putForgotPassword$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation postForgotPassword
   */
  static readonly PostForgotPasswordPath = '/passwordReset';

  /**
   * Send email with password reset link
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postForgotPassword()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postForgotPassword$Response(params?: {

    /**
     * Password reset by email
     */
    body?: ForgotPasswordDto
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, PasswordResetService.PostForgotPasswordPath, 'post');
    if (params) {
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
   * Send email with password reset link
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `postForgotPassword$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postForgotPassword(params?: {

    /**
     * Password reset by email
     */
    body?: ForgotPasswordDto
  }): Observable<void> {

    return this.postForgotPassword$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
