import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ApiService } from '../api';
import { Task } from './task';


@Injectable()
export class TaskService {

  constructor(private api: ApiService) { }

  getTasks(project: string, iteration: number | string, story: number | string): Observable<any[]> {
    const url = `/project/${project}/iteration/${iteration}/story/${story}/task`;
    return this.api.get(url).map(response => {
      if (response.ok) {
        const json = response.json();
        return json.tasks;
      }
      return [];
    });
  }


  getTask(project: string, iteration: number | string, story: number | string, task: number | string): Observable<any | null> {
    const url = `/project/${project}/iteration/${iteration}/story/${story}/task/${task}`;
    return this.api.get(url).map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }

  updateTask(project: string, iteration: number | string, story: number | string,
             index: number | string, task: Task ): Observable<any | null> {
    const url = `/project/${project}/iteration/${iteration}/story/${story}/task/${index}`;
    return this.api.put(url, { task }).map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }
}
