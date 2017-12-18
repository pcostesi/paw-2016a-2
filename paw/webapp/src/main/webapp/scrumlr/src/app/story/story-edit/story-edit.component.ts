import { Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-story-edit',
  templateUrl: './story-edit.component.html',
  styleUrls: ['./story-edit.component.scss']
})
export class StoryEditComponent implements OnInit {
  public form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal) {
    this.form = formBuilder.group({
      title: ['', Validators.required],
    });
  }

  ngOnInit() {
  }

  onSubmit(form: FormGroup) {
  }

}
