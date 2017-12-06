import { Component, OnInit, Input } from '@angular/core';
import { TaskService } from '../task.service';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  @Input() story: any;
  public tasks: any[];

  constructor(private taskService: TaskService) { }

  ngOnInit() {
    const storyId = this.story.storyId;
    const iteration = this.story.iteration;
    const project = this.story.iteration.project;
    this.taskService.getTasks(project.code, iteration.number, storyId)
      .subscribe(result => {
        this.tasks = result;
      });
  }

}
