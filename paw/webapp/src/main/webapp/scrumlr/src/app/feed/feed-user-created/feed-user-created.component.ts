import { Component, OnInit, Input } from '@angular/core';
import { UserCreatedEvent } from 'app/feed/user-created-event';

@Component({
  selector: 'app-feed-user-created',
  templateUrl: './feed-user-created.component.html',
  styleUrls: ['./feed-user-created.component.scss']
})
export class FeedUserCreatedComponent implements OnInit {
  @Input() event: UserCreatedEvent;

  constructor() { }

  ngOnInit() {
  }

}
