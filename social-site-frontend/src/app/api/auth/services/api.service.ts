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

import { PublicUserDetailsDto } from '../models/public-user-details-dto';

@Injectable({
  providedIn: 'root',
})
export class ApiService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getPublicUser
   */
  static readonly GetPublicUserPath = '/publicUser/{id}';

  /**
   * Your GET endpoint.
   *
   * Get public user details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getPublicUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPublicUser$Response(params: {
    id: number;
  }): Observable<StrictHttpResponse<PublicUserDetailsDto>> {

    const rb = new RequestBuilder(this.rootUrl, ApiService.GetPublicUserPath, 'get');
    if (params) {
      rb.path('id', params.id, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<PublicUserDetailsDto>;
      })
    );
  }

  /**
   * Your GET endpoint.
   *
   * Get public user details
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getPublicUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPublicUser(params: {
    id: number;
  }): Observable<PublicUserDetailsDto> {

    return this.getPublicUser$Response(params).pipe(
      map((r: StrictHttpResponse<PublicUserDetailsDto>) => r.body as PublicUserDetailsDto)
    );
  }

}
