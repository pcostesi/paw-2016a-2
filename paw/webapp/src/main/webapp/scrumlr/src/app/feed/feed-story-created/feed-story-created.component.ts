import { Component, OnInit, Input } from '@angular/core';
import { StoryCreatedEvent } from 'app/feed/story-created-event';

@Component({
  selector: 'app-feed-story-created',
  templateUrl: './feed-story-created.component.html',
  styleUrls: ['./feed-story-created.component.scss']
})
export class FeedStoryCreatedComponent implements OnInit {
  @Input() event: StoryCreatedEvent;

  constructor() { }

  ngOnInit() {
  }

}
