import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { StoryService } from '../../story/story.service';
import { IterationService } from '../iteration.service';

@Component({
  selector: 'app-iteration-detail',
  templateUrl: './iteration-detail.component.html',
  styleUrls: ['./iteration-detail.component.scss']
})
export class IterationDetailComponent implements OnInit {
  @Input() iteration: any;
  @Input() expand = true;
  public stories: Observable<any[]>;

  constructor(private iterationService: IterationService,
    private storyService: StoryService) { }

  ngOnInit() {
    const project = this.iteration.project.code;
    const iteration = this.iteration.number;
    this.stories = this.storyService.getStories(project, iteration);
  }

}
