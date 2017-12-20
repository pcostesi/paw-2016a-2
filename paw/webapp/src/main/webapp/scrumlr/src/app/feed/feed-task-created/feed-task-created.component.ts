import { Component, OnInit, Input } from '@angular/core';
import { TaskCreateComponent } from 'app/task/task-create/task-create.component';
import { TaskCreatedEvent } from 'app/feed/task-created-event';

@Component({
  selector: 'app-feed-task-created',
  templateUrl: './feed-task-created.component.html',
  styleUrls: ['./feed-task-created.component.scss']
})
export class FeedTaskCreatedComponent implements OnInit {
  @Input() event: TaskCreatedEvent;

  constructor() { }

  ngOnInit() {
  }

}
