import { Component, OnInit, Input } from '@angular/core';
import { TaskService } from '../task.service';
import { Story } from 'app/story/story';
import { Task } from 'app/task/task';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TaskCreateComponent } from 'app/task/task-create/task-create.component';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  @Input() story: Story;
  public tasks: Task[];

  constructor(private taskService: TaskService,
    private modalService: NgbModal) { }

  ngOnInit() {
    const storyId = this.story.storyId;
    const iteration = this.story.iteration;
    const project = this.story.iteration.project;
    this.taskService.getTasks(project.code, iteration.number, storyId)
      .subscribe(result => {
        this.tasks = result;
      });
  }

  addTask() {
    const modal = this.modalService.open(TaskCreateComponent);
    modal.componentInstance.story = this.story;
  }

}
