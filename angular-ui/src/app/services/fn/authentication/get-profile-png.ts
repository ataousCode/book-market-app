/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface GetProfile$Png$Params {
  filename: string;
}

export function getProfile$Png(http: HttpClient, rootUrl: string, params: GetProfile$Png$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<string>>> {
  const rb = new RequestBuilder(rootUrl, getProfile$Png.PATH, 'get');
  if (params) {
    rb.path('filename', params.filename, {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: 'image/png', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<string>>;
    })
  );
}

getProfile$Png.PATH = '/auth/profile/{filename}';
