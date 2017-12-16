import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-task-score',
  templateUrl: './task-score.component.html',
  styleUrls: ['./task-score.component.scss']
})
export class TaskScoreComponent implements OnInit {
  @Input() task: any;

  constructor() { }

  ngOnInit() {
  }

}
