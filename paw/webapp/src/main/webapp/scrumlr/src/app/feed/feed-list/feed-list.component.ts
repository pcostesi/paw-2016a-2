import { Component, OnInit, Input } from '@angular/core';
import { FeedService } from 'app/feed/feed.service';
import { FeedEvent } from 'app/feed/feed-event';
import { Project } from 'app/project/project';

@Component({
  selector: 'app-feed-list',
  templateUrl: './feed-list.component.html',
  styleUrls: ['./feed-list.component.scss']
})
export class FeedListComponent implements OnInit {
  private _project: Project;

  public feed: FeedEvent[] = [];

  constructor(private feedService: FeedService) { }

  ngOnInit() {
  }

  @Input()
  set project(project: Project) {
    if (project) {
      this._project = project;
      this.feedService.getFeedForProject(project).subscribe(feed => this.feed = feed);
    }
  }

  get project() {
    return this._project;
  }

}
