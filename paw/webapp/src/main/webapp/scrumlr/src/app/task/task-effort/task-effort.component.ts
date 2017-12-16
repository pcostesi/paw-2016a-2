import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-task-effort',
  templateUrl: './task-effort.component.html',
  styleUrls: ['./task-effort.component.scss']
})
export class TaskEffortComponent implements OnInit {
  @Input() task: any;

  constructor() { }

  ngOnInit() {
  }

}
