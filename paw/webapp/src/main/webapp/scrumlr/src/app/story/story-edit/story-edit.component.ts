import { Component, OnInit, Input } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Story } from 'app/story/story';
import { StoryService } from '../story.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-story-edit',
  templateUrl: './story-edit.component.html',
  styleUrls: ['./story-edit.component.scss']
})
export class StoryEditComponent implements OnInit {
  public form: FormGroup;
  @Input() story: Story;

  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal,
    private storyService: StoryService,
    private router: Router) {
    this.form = formBuilder.group({
      title: [undefined, Validators.required],
    });
  }

  ngOnInit() {
    this.form.controls.title.setValue(this.story.title);
  }

  onSubmit(form: FormGroup) {
    if (form.valid) {
      const newTitle = form.controls.title.value;
      this.storyService.updateStory(this.story.iteration.project.code, this.story.iteration.number, this.story.storyId, newTitle)
        .subscribe(ok => {
          if (ok) {
            this.modal.close(ok);
          }
        });
      this.modal.close(form.value);
    }
  }

}
