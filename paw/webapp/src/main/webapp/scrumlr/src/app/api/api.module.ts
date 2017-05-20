import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './api.service';


import {BrowserXhr} from '@angular/http';
import {JSONPBackend, ConnectionBackend} from '@angular/http';
import {CookieXSRFStrategy, XHRBackend} from '@angular/http';
import {BaseRequestOptions, RequestOptions} from '@angular/http';
import {BaseResponseOptions, ResponseOptions} from '@angular/http';
import {Http, Jsonp} from '@angular/http';
import {XSRFStrategy} from '@angular/http';


export function _createDefaultCookieXSRFStrategy() {
  return new CookieXSRFStrategy();
}

export function apiFactory(backend: ConnectionBackend, requestOptions: RequestOptions): Http {
  return new Http(backend, requestOptions);
}

export function jsonpFactory(jsonpBackend: JSONPBackend, requestOptions: RequestOptions): Jsonp {
  return new Jsonp(jsonpBackend, requestOptions);
}

@NgModule({
  imports: [
    CommonModule
  ],
  providers: [
    {provide: ApiService, useFactory: apiFactory, deps: [XHRBackend, RequestOptions]},
    BrowserXhr,
    {provide: RequestOptions, useClass: BaseRequestOptions},
    {provide: ResponseOptions, useClass: BaseResponseOptions},
    {provide: ConnectionBackend, useClass: XHRBackend},
    {provide: XSRFStrategy, useFactory: _createDefaultCookieXSRFStrategy},
  ],
  declarations: []
})
export class ApiModule { }
