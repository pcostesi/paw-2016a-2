import { Injectable } from '@angular/core';
import { ApiService } from '../api';
import { Observable } from 'rxjs/Observable';
import { Response } from '@angular/http';

import { BacklogService } from '../backlog/backlog.service';
import { BacklogItem } from '../backlog/backlog-item';

import { IterationService } from '../iteration/iteration.service';

@Injectable()
export class ProjectService {

  constructor(private api: ApiService,
              private backlogService: BacklogService,
              private iterationService: IterationService) { }

  public getProjects() {
    return this.api.get('/project').map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }

  public getProject(code: string) {
    return this.api.get(`/project/${code}`).map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }

  public getSummary(code: string) {
    const projectObservable = this.getProject(code);
    const backlogObservable = this.backlogService.getBacklogItems(code, 10);
    const iterationsObservable = this.iterationService.getIterations(code, 10);

    return Observable.zip(projectObservable, backlogObservable, iterationsObservable).map(pair => {
      return {
        project: pair[0],
        backlog: pair[1],
        iterations: pair[2],
      };
    });
  }

}
