import { Component, OnInit, Input } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Story } from '../story';
import { StoryService } from '../story.service';
import { Router } from '@angular/router';
import { Iteration } from 'app/iteration/iteration';


@Component({
  selector: 'app-story-create',
  templateUrl: './story-create.component.html',
  styleUrls: ['./story-create.component.scss']
})

export class StoryCreateComponent implements OnInit {
  @Input() iteration: Iteration;
  public form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal,
    private storyService: StoryService,
    private router: Router) {
    this.form = formBuilder.group({
      title: ['', Validators.required],
    });
  }

  ngOnInit() {
  }

  onSubmit(form: FormGroup) {
    if (form.valid) {
      const story = new Story();
      story.title = form.controls.title.value;
      this.storyService.createStory(this.iteration.project.code, this.iteration.number, story)
        .subscribe(ok => {
          if (ok) {
            this.router.navigate(['/project', this.iteration.project.code, '/iteration', this.iteration.code])
          }
        });
      this.modal.close(form.value);
    }
  }

}
