import { Component, OnInit, Input } from '@angular/core';

import { StoryService } from '../../story/story.service';
import { IterationService } from '../iteration.service';

@Component({
  selector: 'app-iteration-detail',
  templateUrl: './iteration-detail.component.html',
  styleUrls: ['./iteration-detail.component.scss']
})
export class IterationDetailComponent implements OnInit {
  @Input() iteration: any;
  public stories: any[];

  constructor(private iterationService: IterationService,
    private storyService: StoryService) { }

  ngOnInit() {
    const project = this.iteration.project.code;
    const iteration = this.iteration.number;
    this.storyService.getStories(project, iteration).subscribe(stories => {
      this.stories = stories;
    });
  }

}
