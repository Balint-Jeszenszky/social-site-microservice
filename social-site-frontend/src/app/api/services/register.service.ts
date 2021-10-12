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

import { RegisterDto } from '../models/register-dto';
import { UserDetailsDto } from '../models/user-details-dto';

@Injectable({
  providedIn: 'root',
})
export class RegisterService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation postRegister
   */
  static readonly PostRegisterPath = '/register';

  /**
   * Register new user
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postRegister()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postRegister$Response(params?: {
    body?: RegisterDto
  }): Observable<StrictHttpResponse<UserDetailsDto>> {

    const rb = new RequestBuilder(this.rootUrl, RegisterService.PostRegisterPath, 'post');
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
   * Register new user
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `postRegister$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  postRegister(params?: {
    body?: RegisterDto
  }): Observable<UserDetailsDto> {

    return this.postRegister$Response(params).pipe(
      map((r: StrictHttpResponse<UserDetailsDto>) => r.body as UserDetailsDto)
    );
  }

  /**
   * Path part for operation getRegisterValidate
   */
  static readonly GetRegisterValidatePath = '/register/validate';

  /**
   * Your GET endpoint.
   *
   * Email verification
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getRegisterValidate()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRegisterValidate$Response(params: {
    key: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, RegisterService.GetRegisterValidatePath, 'get');
    if (params) {
      rb.query('key', params.key, {});
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
   * Your GET endpoint.
   *
   * Email verification
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getRegisterValidate$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRegisterValidate(params: {
    key: string;
  }): Observable<void> {

    return this.getRegisterValidate$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
