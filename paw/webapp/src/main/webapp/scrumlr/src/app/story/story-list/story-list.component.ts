import { Component, OnInit, Input } from '@angular/core';
import { Story } from 'app/story/story';

@Component({
  selector: 'app-story-list',
  templateUrl: './story-list.component.html',
  styleUrls: ['./story-list.component.scss']
})
export class StoryListComponent implements OnInit {
  @Input() stories: Story[];

  constructor() { }

  ngOnInit() {
  }

}
