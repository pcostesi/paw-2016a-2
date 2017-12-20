import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Story } from 'app/story/story';

@Component({
  selector: 'app-story-delete',
  templateUrl: './story-delete.component.html',
  styleUrls: ['./story-delete.component.scss']
})
export class StoryDeleteComponent implements OnInit {
  @Input() story: Story;

  constructor(public modal: NgbActiveModal) { }

  ngOnInit() {
  }

  deleteItem() {
    this.modal.close(this.story);
  }
}

