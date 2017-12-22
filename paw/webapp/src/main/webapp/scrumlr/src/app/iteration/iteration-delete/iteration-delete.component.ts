import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Iteration } from 'app/iteration/iteration';
import {IterationService} from '../iteration.service';

@Component({
  selector: 'app-iteration-delete',
  templateUrl: './iteration-delete.component.html',
  styleUrls: ['./iteration-delete.component.scss']
})
export class IterationDeleteComponent implements OnInit {
  @Input() iteration: Iteration;

  constructor(public modal: NgbActiveModal,
              private iterationService: IterationService) { }

  ngOnInit() {
  }

  deleteIteration(iteration: Iteration) {
    this.iterationService.deleteIteration(iteration.project.code, iteration.number)
      .subscribe(ok => {
        if (ok) {
          this.modal.close(ok);
        }
      });
  }
}
