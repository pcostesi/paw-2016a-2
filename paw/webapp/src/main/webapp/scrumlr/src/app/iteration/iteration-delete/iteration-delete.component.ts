import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-iteration-delete',
  templateUrl: './iteration-delete.component.html',
  styleUrls: ['./iteration-delete.component.scss']
})
export class IterationDeleteComponent implements OnInit {
  @Input() iteration: any;

  constructor(public modal: NgbActiveModal) { }

  ngOnInit() {
  }

  deleteIteration(iteration: any) {
    this.modal.close(iteration)
  }
}
