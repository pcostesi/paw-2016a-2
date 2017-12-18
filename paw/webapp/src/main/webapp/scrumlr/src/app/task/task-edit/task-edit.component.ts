import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-task-edit',
  templateUrl: './task-edit.component.html',
  styleUrls: ['./task-edit.component.scss']
})
export class TaskEditComponent implements OnInit {
  public form: FormGroup;
  @Input() story: any;

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
