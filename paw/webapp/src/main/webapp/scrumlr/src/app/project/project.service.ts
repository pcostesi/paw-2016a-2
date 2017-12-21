import { Injectable } from '@angular/core';
import { ApiService } from '../api';
import { Observable } from 'rxjs/Observable';

import { BacklogService } from '../backlog/backlog.service';

import { IterationService } from '../iteration/iteration.service';
import {Project} from './project';

@Injectable()
export class ProjectService {

  constructor(private api: ApiService,
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
    const iterationsObservable = this.iterationService.getIterations(code, 10);

    return Observable.zip(projectObservable, iterationsObservable).map(pair => {
      return {
        project: pair[0],
        iterations: pair[1],
      };
    });
  }

  public updateProject(project: Project): Observable<Project | null> {
    return this.api.put(`/project/${project.code}`,  project).map(response => {
      if (response.ok) {
        return <Project>response.json();
      }
      return null;
    });
  }


  public deleteProject(project: Project): Observable<boolean> {
    return this.api.delete(`/project/${project.code}`).map(response => {
      if (response.ok) {
        return response.ok;
      }
      return response.ok;
    });
  }

  public createProject(project: Project): Observable<Project | null> {
    return this.api.post(`/project`, project).map(response => {
      if (response.ok) {
        return <Project>response.json();
      }
      return null;

    });
  }
}
