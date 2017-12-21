import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Project } from 'app/project/project';

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
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  onSubmit(form: FormGroup) {
    if (form.valid) {
      const title = form.controls.title.value;
      const desc = form.controls.description.value;
      this.modal.close(form.value);
    }
  }

  ngOnInit() {
  }

}
