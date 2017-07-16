import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ApiService } from '../api';


@Injectable()
export class StoryService {

  constructor(private api: ApiService) { }

  getStories(project: string, iteration: number | string): Observable<any[]> {
    const url = `/project/${project}/iteration/${iteration}/story`;
    return this.api.get(url).map(response => {
      if (response.ok) {
        const json = response.json();
        return json.stories;
      }
      return [];
    });
  }


  getStory(project: string, iteration: number | string, story: number | string): Observable<any | null> {
    const url = `/project/${project}/iteration/${iteration}/story/${story}`;
    return this.api.get(url).map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }
}
