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
        return <Task[]>json.tasks;
      }
      return [];
    });
  }


  getTask(project: string, iteration: number | string, story: number | string, task: number | string): Observable<any | null> {
    const url = `/project/${project}/iteration/${iteration}/story/${story}/task/${task}`;
    return this.api.get(url).map(response => {
      if (response.ok) {
        return <Task>response.json();
      }
      return null;
    });
  }

  updateTask(project: string, iteration: number | string, story: number | string,
    index: number | string, task: Task): Observable<any | null> {
    const url = `/project/${project}/iteration/${iteration}/story/${story}/task/${index}`;
    return this.api.put(url, { task }).map(response => {
      if (response.ok) {
        return <Task>response.json();
      }
      return null;
    });
  }

  deleteTask(project: string, iteration: number, story: number, task: number): Observable<boolean> {
    return this.api.delete(`/project/${project}/iteration/${iteration}/story/${story}/task/${task}`).map(response => {
      return response.ok;
    });
  }

  createTask(project: string, iteration: number, story: number, task: Task): Observable<Task | null> {
    const url = `/project/${project}/iteration/${iteration}/story/${story}/task`;
    return this.api.post(url, task).map(response => {
      if (response.ok) {
        return <Task>response.json();
      }
      return null;
    });
  }
}
