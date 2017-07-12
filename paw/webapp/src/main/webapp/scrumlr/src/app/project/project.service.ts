import { Injectable } from '@angular/core';
import { ApiService } from '../api';
import { Observable } from 'rxjs/Observable';
import { Response } from '@angular/http';

@Injectable()
export class ProjectService {

  constructor(private api: ApiService) { }

  public getProjects() {
    return this.api.get('/project').map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }

}
