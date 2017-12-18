import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-task-delete',
  templateUrl: './task-delete.component.html',
  styleUrls: ['./task-delete.component.scss']
})
export class TaskDeleteComponent implements OnInit {
  @Input() task: any;

  constructor(public modal: NgbActiveModal) { }

  ngOnInit() {
  }

  deleteItem() {
    this.modal.close(this.task);
  }
}

