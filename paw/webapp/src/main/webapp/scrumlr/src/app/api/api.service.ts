import { Injectable } from '@angular/core';
import {Http, XHRBackend, RequestOptions, Request, RequestOptionsArgs, Response, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { environment } from '../../environments/environment';

import { SHA256, HmacSHA256, enc as cryptoEncoding } from 'crypto-js';

@Injectable()
export class ApiService extends Http {
  private username: string;
  private password: string;
  private hasCredentials = false;

  request(url: string|Request, options?: RequestOptionsArgs): Observable<Response> {
    if (!this.hasCredentials) {
      throw "Missing credentials";
    }
    const timestamp = Date.now() / 1000;
    if (typeof url === 'string') {
      if (url.startsWith('/')) {
        url = `${environment.apiEndpoint}${url}`;
      }
      if (!options) {
        options = { headers: new Headers() };
      }
      options.headers!.set('Authorization', this.generateTokenFromUri(timestamp, url, options));
      options.withCredentials = true;
    } else {
    // we have to add the token to the url object
      if (url.url.startsWith('/')) {
        url.url = `${environment.apiEndpoint}${url.url}`;
      }
      url.headers.set('Authorization', this.generateTokenFromRequest(timestamp, url));
      url.withCredentials = true;
    }
    console.log(url, options)
    return super.request(url, options)
      .catch(this.catchAuthError())
      .map(response => response.json());
  }

  private catchAuthError () {
    return (res: Response) => {
      if (res.status === 401 || res.status === 403) {
        // Handle missing credentials as a login window
      }
      return Observable.throw(res);
    };
  }

  private static fqdnApiUri(endpoint) {
    return `${environment.apiEndpoint}/${endpoint}`;
  }

  public setCredentials(username: string, password: string) {
    this.username = username;
    this.password = password;
    this.hasCredentials = true;
  }

  public clearCredentials() {
    this.username = '';
    this.password = '';
    this.hasCredentials = false;
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
    //return SHA256(body);
    return "test";
  }

  public static signWithHMAC(data: string, key: string): string {
    const signed = HmacSHA256(data, key);
    return signed.toString();
  }

  private generateTokenFromUri(timestamp: number, request: string, options?: RequestOptionsArgs): string {
    const data = ApiService.formatFromRequestOptions(timestamp, request, options);
    return ApiService.formatToken(this.username, this.password, data);
  }

  private generateTokenFromRequest(timestamp: number, request: Request): string {
    const data = ApiService.formatFromRequest(timestamp, request);
    return ApiService.formatToken(this.username, this.password, data);
  }

  public static formatToken(apiKey: string, secret: string, data: string): string {
    const signature = ApiService.signWithHMAC(data, secret);
    return `HMAC ${apiKey}:${signature}`;
  }
}
