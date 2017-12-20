import { Component, OnInit, Input } from '@angular/core';
import { Task } from 'app/task/task';
import { TaskScore } from 'app/task/task-score.enum';

@Component({
  selector: 'app-task-score',
  templateUrl: './task-score.component.html',
  styleUrls: ['./task-score.component.scss']
})
export class TaskScoreComponent implements OnInit {
  @Input() task: Task;
  public score = TaskScore;

  constructor() { }

  ngOnInit() {
  }

}
