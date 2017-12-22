import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Task } from 'app/task/task';
import { TaskService } from '../task.service';

@Component({
  selector: 'app-task-delete',
  templateUrl: './task-delete.component.html',
  styleUrls: ['./task-delete.component.scss']
})
export class TaskDeleteComponent implements OnInit {
  @Input() task: Task;

  constructor(public modal: NgbActiveModal,
              private taskService: TaskService) { }

  ngOnInit() {
  }

  deleteItem() {
    this.taskService.deleteTask(this.task.story.iteration.project.code,
      this.task.story.iteration.number, this.task.story.storyId, this.task.taskId)
      .subscribe(ok => {
        if (ok) {
          this.modal.close(ok);
        }
      });
  }
}

