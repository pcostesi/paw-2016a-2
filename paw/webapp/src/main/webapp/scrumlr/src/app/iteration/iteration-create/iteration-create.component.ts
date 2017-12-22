import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Project } from 'app/project/project';
import { NgbDatepicker } from '@ng-bootstrap/ng-bootstrap/datepicker/datepicker';

@Component({
  selector: 'app-iteration-create',
  templateUrl: './iteration-create.component.html',
  styleUrls: ['./iteration-create.component.scss']
})
export class IterationCreateComponent implements OnInit {
  @Input() project: Project;
  public form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal) {
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
      const startDate = form.controls.startDate.value;
      const endDate = form.controls.endDate.value;
      console.log(startDate, endDate)
      this.modal.close(form.value);
    }
  }

  ngOnInit() {
  }

}
