import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { AccountService, MaybeUser } from '../../api';
import { FeedEvent } from 'app/feed/feed-event';
import { FeedService } from 'app/feed/feed.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  public user: MaybeUser;
  public userFeed: FeedEvent[];

  constructor(private route: ActivatedRoute, private feedService: FeedService, private accounts: AccountService) {
    const userObservable = route.params.flatMap(({ username }) => {
      return this.accounts.getUser(username);
    });

    const userFeedObservable = userObservable.flatMap(user => {
      if (!user) { return []; }
      return this.feedService.getFeedForUser(user);
    });

    userObservable.subscribe(user => this.user = user);
    userFeedObservable.subscribe(feed => this.userFeed = feed);
  }

  ngOnInit() { }

}
