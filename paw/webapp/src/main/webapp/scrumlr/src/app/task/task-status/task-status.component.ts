import { Component, OnInit, Input } from '@angular/core';
import { TaskStatus } from '../task-status.enum';

@Component({
  selector: 'app-task-status',
  templateUrl: './task-status.component.html',
  styleUrls: ['./task-status.component.scss']
})
export class TaskStatusComponent implements OnInit {
  @Input() task: any;
  public taskProgress = TaskStatus.NOT_STARTED;

  constructor() { }

  ngOnInit() {
    this.taskProgress = <TaskStatus>this.task.status;
  }

}
