import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { ApiService } from '../api';

import { BacklogItem } from './backlog-item';
import { BacklogActivity } from './backlog-activity.enum';

@Injectable()
export class BacklogService {
  private backlogActivity: Subject<BacklogActivity>;

  constructor(private api: ApiService) {
    this.backlogActivity = new Subject<BacklogActivity>();
  }

  events() {
    return this.backlogActivity.asObservable();
  }

  getBacklogItems(project: string, top?: number): Observable<BacklogItem[]> {
    top = top && top > 0 ? top : 0;
    return this.api.get(`/project/${project}/backlog?top=${top}`).map(response => {
      if (response.ok) {
        const json = response.json();
        this.backlogActivity.next(BacklogActivity.FETCHED);
        return json.backlog;
      }
      return [];
    });
  }


  getBacklogItem(project: string, id: string): Observable<BacklogItem | null> {
    return this.api.get(`/project/${project}/backlog/${id}`).map(response => {
      if (response.ok) {
        const json = response.json();
        this.backlogActivity.next(BacklogActivity.FETCHED);
        return <BacklogItem>json || null;
      }
      return null;
    });
  }


  createBacklogItem(project: string, title: string, description: string): Observable<boolean> {
    const data = {title, description};
    return this.api.post(`/project/${project}/backlog`, data).map(response => {
      if (response.ok) {
        this.backlogActivity.next(BacklogActivity.CREATED);
        return true;
      }
      return false;
    });
  }


  editBacklogItem(project: string, item: BacklogItem, title: string, description: string): Observable<boolean> {
    const data = {title, description};
    return this.api.put(`/project/${project}/backlog/${item.backlogItemId}`, data).map(response => {
      if (response.ok) {
        this.backlogActivity.next(BacklogActivity.EDITED);
        return true;
      }
      return false;
    });
  }

  deleteBacklogItem(project: string, item: BacklogItem): Observable<boolean> {
    return this.api.delete(`/project/${project}/backlog/${item.backlogItemId}`)
      .map(response => {
        if (response.ok) {
            this.backlogActivity.next(BacklogActivity.DELETED);
        }
        return response.ok;
      });
  }



}
