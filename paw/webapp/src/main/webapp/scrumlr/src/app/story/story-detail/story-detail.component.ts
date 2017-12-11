import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-story-detail',
  templateUrl: './story-detail.component.html',
  styleUrls: ['./story-detail.component.scss']
})
export class StoryDetailComponent implements OnInit {
  @Input() story: any;
  public isOpen = false;
  constructor() { }

  ngOnInit() { }

}
