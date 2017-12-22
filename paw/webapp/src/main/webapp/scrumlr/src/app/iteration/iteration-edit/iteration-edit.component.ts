import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Iteration } from 'app/iteration/iteration';

@Component({
  selector: 'app-iteration-edit',
  templateUrl: './iteration-edit.component.html',
  styleUrls: ['./iteration-edit.component.scss']
})
export class IterationEditComponent implements OnInit {
  @Input() iteration: Iteration;
  public form: FormGroup;


  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal) {
    this.form = formBuilder.group({
      startDate: [undefined, Validators.required],
      endDate: [undefined, Validators.required],
    });
  }

  setDate(iteration: Iteration) {

    const iterationStart = new Date(iteration.startDate);
    const iterationEnd = new Date(iteration.endDate);

    if (!this.form) { return; }
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
    this.setDate(this.iteration);
  }

}

