import { Injectable } from '@angular/core';
import { ApiService } from 'app/api/api.service';
import { FeedEvent } from 'app/feed/feed-event';
import { Observable } from 'rxjs/Observable';
import { UserProfile } from 'app/api/account.service';
import { BaseFeedEvent } from 'app/feed/base-feed-event';
import { Project } from 'app/project/project';

@Injectable()
export class FeedService {

  constructor(private api: ApiService) { }

  getFeedForUser(user?: UserProfile) {
    const url = user ? `/feed/user/${user.username}` : '/feed';
    return this.api.get(url).map(response => {
      if (response.ok) {
        const parsed = response.json()
        return <FeedEvent[]>parsed.events;
      }
      throw Error(response.statusText || response.status.toString());
    });
  }

  getFeedForProject(project: Project) {

    return this.api.get(`/feed/project/${project.code}`).map(response => {
      if (response.ok) {
        const parsed = response.json()
        return <FeedEvent[]>parsed.events;
      }
      throw Error(response.statusText || response.status.toString());
    });
  }
}
