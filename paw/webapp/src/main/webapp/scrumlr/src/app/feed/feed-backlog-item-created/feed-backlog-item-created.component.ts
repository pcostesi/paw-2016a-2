import { Component, OnInit, Input } from '@angular/core';
import { BacklogItemCreatedEvent } from 'app/feed/backlog-item-created-event';

@Component({
  selector: 'app-feed-backlog-item-created',
  templateUrl: './feed-backlog-item-created.component.html',
  styleUrls: ['./feed-backlog-item-created.component.scss']
})
export class FeedBacklogItemCreatedComponent implements OnInit {
  @Input() event: BacklogItemCreatedEvent;

  constructor() { }

  ngOnInit() {
  }

}
