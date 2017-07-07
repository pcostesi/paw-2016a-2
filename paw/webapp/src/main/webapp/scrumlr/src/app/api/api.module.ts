import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {BrowserXhr} from '@angular/http';
import {JSONPBackend, ConnectionBackend} from '@angular/http';
import {CookieXSRFStrategy, XHRBackend} from '@angular/http';
import {BaseRequestOptions, RequestOptions} from '@angular/http';
import {BaseResponseOptions, ResponseOptions} from '@angular/http';
import {Http, Jsonp} from '@angular/http';
import {XSRFStrategy} from '@angular/http';

import { ApiService } from './api.service';
import { AccountService } from './account.service';
import { LoginComponent } from './login/login.component';
import { BadgeComponent } from './badge/badge.component';
import { LoginGuard } from './login.guard';



export function _createDefaultCookieXSRFStrategy() {
  return new CookieXSRFStrategy();
}

export function apiFactory(backend: ConnectionBackend, requestOptions: RequestOptions): Http {
  return new ApiService(backend, requestOptions);
}

export function jsonpFactory(jsonpBackend: JSONPBackend, requestOptions: RequestOptions): Jsonp {
  return new Jsonp(jsonpBackend, requestOptions);
}

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule, // we need it for routerLink
  ],
  providers: [
    {provide: ApiService, useFactory: apiFactory, deps: [XHRBackend, RequestOptions]},
    BrowserXhr,
    {provide: RequestOptions, useClass: BaseRequestOptions},
    {provide: ResponseOptions, useClass: BaseResponseOptions},
    {provide: ConnectionBackend, useClass: XHRBackend},
    {provide: XSRFStrategy, useFactory: _createDefaultCookieXSRFStrategy},
    AccountService,
    LoginGuard,
  ],
  declarations: [LoginComponent, BadgeComponent],
  entryComponents: [LoginComponent],
  exports: [BadgeComponent]
})
export class ApiModule { }
