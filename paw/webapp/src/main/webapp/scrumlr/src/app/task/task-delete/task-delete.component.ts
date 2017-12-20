import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Task } from 'app/task/task';

@Component({
  selector: 'app-task-delete',
  templateUrl: './task-delete.component.html',
  styleUrls: ['./task-delete.component.scss']
})
export class TaskDeleteComponent implements OnInit {
  @Input() task: Task;

  constructor(public modal: NgbActiveModal) { }

  ngOnInit() {
  }

  deleteItem() {
    this.modal.close(this.task);
  }
}

