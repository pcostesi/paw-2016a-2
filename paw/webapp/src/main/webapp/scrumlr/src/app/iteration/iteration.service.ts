import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ApiService } from '../api';

@Injectable()
export class IterationService {

  constructor(private api: ApiService) { }


  getIterations(project: string, top?: number): Observable<any[]> {
    top = top && top > 0 ? top : 0;
    return this.api.get(`/project/${project}/iteration?top=${top}`).map(response => {
      if (response.ok) {
        const json = response.json();
        return json.iterations;
      }
      return [];
    });
  }


  getIteration(project: string, iteration: number): Observable<any | null> {
    return this.api.get(`/project/${project}/iteration/${iteration}`).map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }
}
