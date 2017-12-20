import { Component, OnInit, Input } from '@angular/core';
import { ProjectCreatedEvent } from 'app/feed/project-created-event';

@Component({
  selector: 'app-feed-project-created',
  templateUrl: './feed-project-created.component.html',
  styleUrls: ['./feed-project-created.component.scss']
})
export class FeedProjectCreatedComponent implements OnInit {
  @Input() event: ProjectCreatedEvent;

  constructor() { }

  ngOnInit() {
  }

}
