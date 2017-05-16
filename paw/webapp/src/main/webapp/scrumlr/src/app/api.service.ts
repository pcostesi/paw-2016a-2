import { Injectable } from '@angular/core';
import {Http, XHRBackend, RequestOptions, Request, RequestOptionsArgs, Response, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

@Injectable()
export class ApiService extends Http {

  request(url: string|Request, options?: RequestOptionsArgs): Observable<Response> {
    if (typeof url === 'string') {
      if (!options) {
        options = { headers: new Headers() };
      }
      options.headers!.set('Authorization', this.generateToken());
    } else {
    // we have to add the token to the url object
      url.headers.set('Authorization', this.generateToken());
    }
    return super.request(url, options).catch(this.catchAuthError());
  }

  private catchAuthError () {
    return (res: Response) => {
      if (res.status === 401 || res.status === 403) {
        // Handle missing credentials as a login window
      }
      return Observable.throw(res);
    };
  }

  private formatRequestString(request: Request, options?: RequestOptionsArgs): string {
    return "";
  }

  private signWithHMAC(): string {
    return "";
  }

  private generateToken(): string {
    let apiKey = "";
    let signature = "";
    return `HMAC ${apiKey}:${signature}`;
  }
}
