import { Injectable } from '@angular/core';
import { ApiService } from 'app/api/api.service';
import { FeedEvent } from 'app/feed/feed-event';
import { Observable } from 'rxjs/Observable';
import { UserProfile } from 'app/api/account.service';
import { BaseFeedEvent } from 'app/feed/base-feed-event';

@Injectable()
export class FeedService {

  constructor(private api: ApiService) { }

  getFeedForUser() {

    return this.api.get(`/feed`).map(response => {
      if (response.ok) {
        const parsed = response.json()
        return <FeedEvent[]>parsed.events;
      }
      throw Error(response.statusText || response.status.toString());
    });
  }
}
