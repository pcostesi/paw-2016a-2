import { TestBed, inject } from '@angular/core/testing';

import {BaseRequestOptions, ConnectionBackend, Http, RequestOptions, Request} from '@angular/http';
import {async, fakeAsync, tick} from '@angular/core/testing';
import {Response, ResponseOptions} from '@angular/http';
import {MockBackend, MockConnection} from '@angular/http/testing';

import { ApiService } from './api.service';

describe('ApiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ApiService,
        {provide: ConnectionBackend, useClass: MockBackend},
        {provide: RequestOptions, useClass: BaseRequestOptions}
      ]
    });
  });



  it('should sign data using hmac', inject([ApiService], (service: ApiService) => {
    const data = 'hmac data here';
    const key = 'zekrit key iz zekrit';
    const signature = '26UIOPaqZJJ9Y8l0kEYqvYFtPm9sPdfqNDv+H3WTWc8=';
    const signed = ApiService.signWithHMAC(data, key);
    expect(signed).toEqual(signature);
  }));

  it('should correctly format a Request', inject([ApiService], (service: ApiService) => {
    const timestamp = 1494995346;
    const expectedDigest = 'MAowCjE0OTQ5OTUzNzAKdGVzdA==';
    const request = new Request(new RequestOptions({
      method: 'GET',
      url: '/test/me'
    }));
    const digest = ApiService.formatFromRequest(timestamp, request);
    expect(digest).toEqual(expectedDigest);
  }));

  
  it('should correctly format a header token', inject([ApiService], (service: ApiService) => {
    const apiKey = 'test-user';
    const secret = 'zekrit key iz zekrit';
    const data = 'MAowCjE0OTQ5OTUzNzAKdGVzdA==';
    const expectedToken = 'HMAC test-user:Mdwm6JiO1BUuSIR5mBummB62M6VQUFgpyWd1u+9TEwE=';
    const token = ApiService.formatToken(apiKey, secret, data);
    expect(token).toEqual(expectedToken);
  }));
});
