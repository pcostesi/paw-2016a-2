import { Component, OnInit, Input } from '@angular/core';
import { Task } from 'app/task/task';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TaskEditComponent } from 'app/task/task-edit/task-edit.component';
import { TaskDeleteComponent } from 'app/task/task-delete/task-delete.component';

@Component({
  selector: 'app-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.css']
})
export class TaskDetailComponent implements OnInit {
  @Input() task: Task;

  constructor(private modalService: NgbModal) { }

  ngOnInit() {
  }

  editTask(task: Task) {
    const ref = this.modalService.open(TaskEditComponent);
    ref.componentInstance.task = this.task;
  }

  deleteTask(task: Task) {
    const ref = this.modalService.open(TaskDeleteComponent);
    ref.componentInstance.task = task;
  }

}
