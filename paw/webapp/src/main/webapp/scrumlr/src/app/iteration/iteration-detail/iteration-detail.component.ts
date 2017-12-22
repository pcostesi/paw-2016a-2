import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { StoryService } from '../../story/story.service';
import { IterationService } from '../iteration.service';

import { IterationEditComponent } from '../iteration-edit/iteration-edit.component';
import { IterationDeleteComponent } from '../iteration-delete/iteration-delete.component';
import { Story } from 'app/story/story';
import { Iteration } from 'app/iteration/iteration';
import { StoryCreateComponent } from 'app/story/story-create/story-create.component';

@Component({
  selector: 'app-iteration-detail',
  templateUrl: './iteration-detail.component.html',
  styleUrls: ['./iteration-detail.component.scss']
})
export class IterationDetailComponent implements OnInit {
  private _iteration: Iteration;
  public stories: Observable<Story[]>;

  constructor(private iterationService: IterationService,
    private modalService: NgbModal,
    private storyService: StoryService) { }

  ngOnInit() { }

  @Input()
  set iteration(value: Iteration) {
    if (!value) {
      return;
    }
    this._iteration = value;
    const project = this.iteration.project.code;
    const iteration = this.iteration.number;
    this.stories = this.storyService.getStories(project, iteration);
  }

  get iteration() {
    return this._iteration;
  }

  editIteration(iteration: Iteration) {
    const ref = this.modalService.open(IterationEditComponent);
    console.log(iteration)
    ref.componentInstance.iteration = iteration;
  }

  deleteIteration(iteration: Iteration) {
    const ref = this.modalService.open(IterationDeleteComponent);
    ref.componentInstance.iteration = iteration;
  }

  newStory(iteration: Iteration) {
    const ref = this.modalService.open(StoryCreateComponent);
    ref.componentInstance.iteration = iteration;
  }

}
