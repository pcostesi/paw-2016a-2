import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Iteration } from 'app/iteration/iteration';
import {IterationService} from '../iteration.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-iteration-edit',
  templateUrl: './iteration-edit.component.html',
  styleUrls: ['./iteration-edit.component.scss']
})
export class IterationEditComponent implements OnInit {
  @Input() iteration: Iteration;
  public form: FormGroup;


  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal,
    private iterationService: IterationService,
    private router: Router) {
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
      const iteration = new Iteration();
      const begin = form.controls.startDate.value;
      iteration.startDate = begin.day + '/' + begin.month + '/' + begin.year;
      const end = form.controls.endDate.value;
      iteration.endDate = end.day + '/' + end.month + '/' + end.year;
      this.iterationService.updateIteration(this.iteration.project.code, iteration)
        .subscribe(ok => {
        if (ok) {
          this.router.navigate(['/project', this.iteration.project.code])
        }
      });
      this.modal.close(form.value);
    }
  }

  ngOnInit() {
    this.setDate(this.iteration);
  }

}

