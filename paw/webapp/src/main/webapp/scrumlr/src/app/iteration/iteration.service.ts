import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ApiService } from '../api';
import {Iteration} from './iteration';

@Injectable()
export class IterationService {

  constructor(private api: ApiService) { }


  public getIterations(project: string, top?: number): Observable<any[]> {
    top = top && top > 0 ? top : 0;
    return this.api.get(`/project/${project}/iteration?top=${top}`).map(response => {
      if (response.ok) {
        const json = response.json();
        return json.iterations;
      }
      return [];
    });
  }


  public getIteration(project: string, iteration: number): Observable<any | null> {
    return this.api.get(`/project/${project}/iteration/${iteration}`).map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }

  public updateIteration(project: string, iteration: Iteration): Observable<Iteration | null> {
    return this.api.put(`/project/${project}/iteration/${iteration.number}`, { iteration }).map(response => {
      if (response.ok) {
        return <Iteration>response.json();
      }
      return null;
    });
  }

  public deleteIteration(project: string, iteration: number): Observable<boolean> {
    return this.api.delete(`/project/${project}/iteration/${iteration}`).map(response => {
      return response.ok;
    });
  }

  public createIteration(project: string, iteration: Iteration): Observable<Iteration | null> {
    return this.api.post(`/project/${project}/iteration`,  iteration ).map(response => {
      if (response.ok) {
        return <Iteration>response.json();
      }
      return null;
    });
  }
}
