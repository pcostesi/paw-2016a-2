import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'scrumlr-story-list',
  templateUrl: './story-list.component.html',
  styleUrls: ['./story-list.component.scss']
})
export class StoryListComponent implements OnInit {
  @Input() stories: any[];

  constructor() { }

  ngOnInit() {
  }

}
