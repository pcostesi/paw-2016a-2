import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { BacklogService } from '../backlog.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {
  @Input() project: string;

  public form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private backlogService: BacklogService,
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
      this.backlogService.createBacklogItem(this.project, title, desc)
        .subscribe(ok => {
          if (ok) {
            this.modal.close(ok);
          }
        });
    }
  }

  ngOnInit() {
  }

}
