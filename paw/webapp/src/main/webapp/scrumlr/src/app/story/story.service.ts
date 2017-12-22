import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ApiService } from '../api';
import { Story } from './story'


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

  updateStory(project: string, iteration: number | string, story: number | string, title: string): Observable<any | null> {
    console.log(project);
    const url = `/project/${project}/iteration/${iteration}/story/${story}`;
    return this.api.put(url, { title } ).map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }

  createStory(project: string, iteration: number, story: Story): Observable<Story | null> {
    const url = `/project/${project}/iteration/${iteration}/story/`;
    return this.api.post(url, story).map(response => {
      if (response.ok) {
        return response.json();
      }
      return null;
    });
  }

  deleteStory(story: Story): Observable<boolean> {
    return this.api.delete(`/project/${story.iteration.project.code}/iteration/${story.iteration.number}/story/${story.storyId}`)
      .map(response => {
        return response.ok;
      });
  }
}
