import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Story } from 'app/story/story';
import { StoryService } from '../story.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-story-delete',
  templateUrl: './story-delete.component.html',
  styleUrls: ['./story-delete.component.scss']
})
export class StoryDeleteComponent implements OnInit {
  @Input() story: Story;

  constructor(public modal: NgbActiveModal,
              private storyService: StoryService,
              private router: Router) { }

  ngOnInit() {
  }

  deleteStory() {
    this.storyService.deleteStory(this.story)
      .subscribe(ok => {
      if (ok) {
        this.router.navigate(['project', this.story.iteration.project.code, '/iteration', this.story.iteration.number]);
        this.modal.close(ok);
      }
    });
  }
}

