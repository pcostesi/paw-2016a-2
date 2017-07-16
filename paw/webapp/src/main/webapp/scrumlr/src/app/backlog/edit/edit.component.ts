import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { BacklogService } from '../backlog.service';
import { BacklogItem } from '../backlog-item';

@Component({
  selector: 'scrumlr-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {
  @Input() item: BacklogItem;
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
      this.backlogService.editBacklogItem(this.project, this.item, title, desc)
        .subscribe(ok => {
          if (ok) {
            this.modal.close(ok);
          }
        });
    }
  }

  ngOnInit() {
    this.form.controls.title.setValue(this.item.title);
    this.form.controls.description.setValue(this.item.description);
  }

}
