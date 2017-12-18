import { Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-story-create',
  templateUrl: './story-create.component.html',
  styleUrls: ['./story-create.component.scss']
})
export class StoryCreateComponent implements OnInit {

  public form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal) {
    this.form = formBuilder.group({
      title: ['', Validators.required],
    });
  }

  ngOnInit() {
  }

  onSubmit(form: FormGroup) { }

}
