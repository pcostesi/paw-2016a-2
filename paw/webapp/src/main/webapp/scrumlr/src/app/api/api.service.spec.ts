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
        {
          provide: ApiService,
          deps: [ConnectionBackend, RequestOptions],
          useFactory:
            (backend: ConnectionBackend, defaultOptions: RequestOptions) => {
                return new ApiService(backend, defaultOptions);
            }
         },
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
    const expectedDigest = 'R0VUCmFwcGxpY2F0aW9uL2pzb24KMTQ5NDk5NTM3MAp0ZXN0';
    const request = new Request(new RequestOptions({
      method: 'GET',
      url: '/test/me'
    }));
    const digest = ApiService.formatFromRequest(timestamp, request);
    expect(digest).toEqual(expectedDigest);
  }));

  
  it('should correctly format from request options', inject([ApiService], (service: ApiService) => {
    const timestamp = 1494995346;
    const expectedDigest = 'R0VUCmFwcGxpY2F0aW9uL2pzb24KMTQ5NDk5NTM3MAp0ZXN0';
    const uri = '/test/me';
    const options = new RequestOptions({
      method: 'GET'
    });
    const digest = ApiService.formatFromRequestOptions(timestamp, uri, options);
    expect(digest).toEqual(expectedDigest);
  }));

  it('should correctly format a header token', inject([ApiService], (service: ApiService) => {
    const apiKey = 'test-user';
    const secret = 'zekrit key iz zekrit';
    const data = 'R0VUCmFwcGxpY2F0aW9uL2pzb24KMTQ5NDk5NTM3MAp0ZXN0';
    const expectedToken = 'HMAC test-user:2A8nNoV5EDYskTqK9bgpQ96cGEsw4Xe1i3/1x35+M8s=';
    const token = ApiService.formatToken(apiKey, secret, data);
    expect(token).toEqual(expectedToken);
  }));

  it('should add the auth header to a request (async)', async(inject([ApiService, ConnectionBackend], 
  (service: ApiService, backend: MockBackend) => {
    const apiKey = 'test-user';
    const secret = 'zekrit key iz zekrit';
    const uri = '/users/me';

    const timestamp = Date.now() / 1000;
    const options = new RequestOptions({
      method: 'GET'
    });

    const digest = ApiService.formatFromRequestOptions(timestamp, uri, options);
    const token = ApiService.formatToken(apiKey, secret, digest);


    backend.connections.subscribe(connection => {   
      expect(connection.request).toBeDefined();
      expect(connection.request.headers.get('Authorization'))
        .toEqual(token);
    });
    service.setCredentials(apiKey, secret);
    service.request(uri);
  })));
});
