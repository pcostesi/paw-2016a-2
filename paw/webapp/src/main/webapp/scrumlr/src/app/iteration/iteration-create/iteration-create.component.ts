import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Project } from 'app/project/project';
import { NgbDatepicker } from '@ng-bootstrap/ng-bootstrap/datepicker/datepicker';
import { Iteration } from '../iteration';
import { IterationService } from '../iteration.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-iteration-create',
  templateUrl: './iteration-create.component.html',
  styleUrls: ['./iteration-create.component.scss']
})
export class IterationCreateComponent implements OnInit {
  @Input() project: Project;
  public form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal,
    private iterationService: IterationService,
    private router: Router) {
    this.form = formBuilder.group({
      startDate: [undefined, Validators.required],
      endDate: [undefined, Validators.required],
    });

    const iterationStart = new Date();
    const iterationEnd = new Date();
    iterationEnd.setDate(iterationEnd.getDate() + 15);

    this.form.controls.startDate.setValue({
      year: iterationStart.getFullYear(),
      month: iterationStart.getMonth() + 1,
      day: iterationStart.getDate()
    });
    this.form.controls.endDate.setValue({
      year: iterationEnd.getFullYear(),
      month: iterationEnd.getMonth() + 1,
      day: iterationEnd.getDate()
    });
  }

  onSubmit(form: FormGroup) {
    if (form.valid) {
      const iteration = <Iteration>{};
      const begin = form.controls.startDate.value;
      iteration.startDate = begin.day + '/' + begin.month + '/' + begin.year;
      const end = form.controls.endDate.value;
      iteration.endDate = end.day + '/' + end.month + '/' + end.year;
      this.iterationService.createIteration(this.project.code, iteration)
        .subscribe(ok => {
          if (ok) {
            this.modal.close(ok);
          }
        });
      this.modal.close(form.value);
    }
  }

  ngOnInit() {
  }

}
