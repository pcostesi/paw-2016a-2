import { Component, OnInit, Input } from '@angular/core';
import { Task } from 'app/task/task';
import { TaskPriority } from 'app/task/task-priority.enum';

@Component({
  selector: 'app-task-priority',
  templateUrl: './task-priority.component.html',
  styleUrls: ['./task-priority.component.scss']
})
export class TaskPriorityComponent implements OnInit {
  @Input() task: Task;
  public priority = TaskPriority;

  constructor() { }

  ngOnInit() {
  }

}
