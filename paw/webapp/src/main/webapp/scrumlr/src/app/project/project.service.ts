import { Injectable } from '@angular/core';
import { ApiService } from '../api';
import { Observable } from 'rxjs/Observable';
import { Response } from '@angular/http';

import { BacklogService } from '../backlog/backlog.service';
import { BacklogItem } from '../backlog/backlog-item';

@Injectable()
export class ProjectService {

  constructor(private api: ApiService,
              private backlogService: BacklogService) { }

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
    const backlogObservable = this.backlogService.getBacklogItems(code);

    return Observable.zip(projectObservable, backlogObservable).map(pair => {
      return {
        project: pair[0],
        backlog: pair[1],
      };
    });
  }

}
