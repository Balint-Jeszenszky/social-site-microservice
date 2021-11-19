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
export class DeleteSocialService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation deleteDeleteUserId
   */
  static readonly DeleteDeleteUserIdPath = '/delete/{userId}';

  /**
   * Delete everithing connected to user
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteDeleteUserId()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteDeleteUserId$Response(params: {
    userId: number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, DeleteSocialService.DeleteDeleteUserIdPath, 'delete');
    if (params) {
      rb.path('userId', params.userId, {});
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
   * Delete everithing connected to user
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `deleteDeleteUserId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteDeleteUserId(params: {
    userId: number;
  }): Observable<void> {

    return this.deleteDeleteUserId$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
