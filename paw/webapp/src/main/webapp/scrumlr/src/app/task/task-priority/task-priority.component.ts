import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-task-priority',
  templateUrl: './task-priority.component.html',
  styleUrls: ['./task-priority.component.scss']
})
export class TaskPriorityComponent implements OnInit {
  @Input() task: any;

  constructor() { }

  ngOnInit() {
  }

}
