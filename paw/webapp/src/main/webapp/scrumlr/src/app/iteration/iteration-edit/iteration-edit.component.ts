import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-iteration-edit',
  templateUrl: './iteration-edit.component.html',
  styleUrls: ['./iteration-edit.component.scss']
})
export class IterationEditComponent implements OnInit {
  public form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal) {
    this.form = formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ngOnInit() {
  }

  onSubmit(form: FormGroup) {
    if (form.valid) {
      const title = form.controls.title.value;
      const desc = form.controls.description.value;
      this.modal.close(form.value);
    }
  }

}
