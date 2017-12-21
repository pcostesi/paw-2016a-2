import { Component, OnInit, Input } from '@angular/core';
import { Story } from 'app/story/story';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { StoryEditComponent } from 'app/story/story-edit/story-edit.component';
import { StoryDeleteComponent } from 'app/story/story-delete/story-delete.component';

@Component({
  selector: 'app-story-detail',
  templateUrl: './story-detail.component.html',
  styleUrls: ['./story-detail.component.scss']
})
export class StoryDetailComponent implements OnInit {
  @Input() story: Story;
  public isOpen = false;
  constructor(private modalService: NgbModal) { }

  ngOnInit() { }

  editStory(story: Story) {
    const ref = this.modalService.open(StoryEditComponent);
    ref.componentInstance.story = story;
  }

  deleteStory(story: Story) {
    const ref = this.modalService.open(StoryDeleteComponent);
    ref.componentInstance.story = story;
  }

}
