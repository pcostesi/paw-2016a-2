import { Component, OnInit, Input } from '@angular/core';
import { Task } from 'app/task/task';

@Component({
  selector: 'app-task-effort',
  templateUrl: './task-effort.component.html',
  styleUrls: ['./task-effort.component.scss']
})
export class TaskEffortComponent implements OnInit {
  @Input() task: Task;

  constructor() { }

  ngOnInit() {
  }

}
