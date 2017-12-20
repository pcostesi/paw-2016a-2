import { Component, OnInit, Input } from '@angular/core';
import { Story } from 'app/story/story';

@Component({
  selector: 'app-story-detail',
  templateUrl: './story-detail.component.html',
  styleUrls: ['./story-detail.component.scss']
})
export class StoryDetailComponent implements OnInit {
  @Input() story: Story;
  public isOpen = false;
  constructor() { }

  ngOnInit() { }

}
