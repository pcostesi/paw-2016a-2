import { Injectable } from '@angular/core';
import { Http, XHRBackend, RequestOptions, Request, RequestOptionsArgs,
  Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { SHA256, HmacSHA256, enc as cryptoEncoding } from 'crypto-js';

import { environment } from '../../environments/environment';
import { LoginEvent } from '.';

@Injectable()
export class ApiService extends Http {
  private username: string;
  private password: string;
  private hasCredentials = false;
  private loginStatus: Subject<LoginEvent> = new Subject<LoginEvent>();

  private static fqdnApiUri(endpoint) {
    return `${environment.apiEndpoint}/${endpoint}`;
  }

  public static formatFromRequestOptions(timestamp: number, request: string, options?: RequestOptionsArgs) {
    const method = (options && options.method) || 'GET';
    const dateString = timestamp - (timestamp % 30) + 30;
    const body = (options && options.body) || '';
    const bodyDigest = ApiService.getBodyDigest(body);
    return btoa(`${method}\n${dateString}\n${bodyDigest}`);
  }

  public static formatFromRequest(timestamp: number, request: Request) {
    const method = request.method.valueOf() || 'GET';
    const dateString = timestamp - (timestamp % 30) + 30;
    const bodyDigest = ApiService.getBodyDigest(request.text);
    return btoa(`${method}\n${dateString}\n${bodyDigest}`);
  }

  private static getBodyDigest(body): string {
    // XXX: Fix this first in java, then here. :(
    // return SHA256(body);
    return 'test';
  }

  public static signWithHMAC(data: string, key: string): string {
    const signed = HmacSHA256(data, key);
    return signed.toString();
  }

  public static formatToken(apiKey: string, secret: string, data: string): string {
    const signature = ApiService.signWithHMAC(data, secret);
    return `HMAC ${apiKey}:${signature}`;
  }

  request(url: string|Request, options?: RequestOptionsArgs): Observable<Response> {
    const timestamp = Date.now() / 1000;
    if (typeof url === 'string') {
      if (url.startsWith('/')) {
        url = `${environment.apiEndpoint}${url}`;
      }
      if (this.hasCredentials) {
        if (!options) {
          options = { headers: new Headers() };
        }
        options.headers!.set('Authorization', this.generateTokenFromUri(timestamp, url, options));
        options.withCredentials = true;
      }
    } else {
    // we have to add the token to the url object
      if (url.url.startsWith('/')) {
        url.url = `${environment.apiEndpoint}${url.url}`;
      }
      if (this.hasCredentials) {
        url.headers.set('Authorization', this.generateTokenFromRequest(timestamp, url));
        url.withCredentials = true;
      }
    }
    return super.request(url, options)
      .catch(this.catchAuthError())
      .map(response => response.json());
  }

  private catchAuthError () {
    return (res: Response) => {
      if (res.status === 401 || res.status === 403) {
        // Handle missing credentials as a login window
      }
      this.loginStatus.next(LoginEvent.BAD_CREDENTIALS);
      return Observable.throw(res);
    };
  }

  public loginStatusTopic(): Observable<LoginEvent> {
    return this.loginStatus.asObservable();
  }

  public setCredentials(username: string, password: string) {
    this.username = username;
    this.password = password;
    this.hasCredentials = true;
    this.loginStatus.next(LoginEvent.SET_CREDENTIALS);
    return this;
  }

  public clearCredentials() {
    this.username = '';
    this.password = '';
    this.hasCredentials = false;
    this.loginStatus.next(LoginEvent.CLEARED_CREDENTIALS);
    return this;
  }

  public hasCredentialsSet() {
    return this.hasCredentials;
  }

  private generateTokenFromUri(timestamp: number, request: string, options?: RequestOptionsArgs): string {
    const data = ApiService.formatFromRequestOptions(timestamp, request, options);
    return ApiService.formatToken(this.username, this.password, data);
  }

  private generateTokenFromRequest(timestamp: number, request: Request): string {
    const data = ApiService.formatFromRequest(timestamp, request);
    return ApiService.formatToken(this.username, this.password, data);
  }

}
