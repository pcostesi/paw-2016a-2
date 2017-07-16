import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ApiService } from '../api';


@Injectable()
export class TaskService {

  constructor(private api: ApiService) { }

  getTasks(project: string, iteration: number | string, story: number | string): Observable<any[]> {
    const url = `/project/${project}/iteration/${iteration}/story/${story}/task`;
    return this.api.get(url).map(response => {
      if (response.ok) {
        const json = response.json();
        return json.stories;
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
}
