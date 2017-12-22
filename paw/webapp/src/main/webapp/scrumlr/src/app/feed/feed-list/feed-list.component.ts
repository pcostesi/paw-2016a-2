import { Component, OnInit, Input } from '@angular/core';
import { FeedService } from 'app/feed/feed.service';
import { FeedEvent } from 'app/feed/feed-event';

@Component({
  selector: 'app-feed-list',
  templateUrl: './feed-list.component.html',
  styleUrls: ['./feed-list.component.scss']
})
export class FeedListComponent implements OnInit {

  @Input() feed: FeedEvent[] = [];

  constructor(private feedService: FeedService) { }

  ngOnInit() {
  }

}
