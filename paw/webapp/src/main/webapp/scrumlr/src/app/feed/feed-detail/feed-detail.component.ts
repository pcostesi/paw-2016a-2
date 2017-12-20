import { Component, OnInit, Input } from '@angular/core';
import { FeedEvent } from 'app/feed/feed-event';

@Component({
  selector: 'app-feed-detail',
  templateUrl: './feed-detail.component.html',
  styleUrls: ['./feed-detail.component.scss']
})
export class FeedDetailComponent implements OnInit {
  @Input() event: FeedEvent;

  constructor() { }

  ngOnInit() {
  }

}
