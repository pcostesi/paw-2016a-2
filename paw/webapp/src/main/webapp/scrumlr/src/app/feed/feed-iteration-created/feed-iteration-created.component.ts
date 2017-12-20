import { Component, OnInit, Input } from '@angular/core';
import { IterationCreatedEvent } from 'app/feed/iteration-created-event';

@Component({
  selector: 'app-feed-iteration-created',
  templateUrl: './feed-iteration-created.component.html',
  styleUrls: ['./feed-iteration-created.component.scss']
})
export class FeedIterationCreatedComponent implements OnInit {
  @Input() event: IterationCreatedEvent;

  constructor() { }

  ngOnInit() {
  }

}
