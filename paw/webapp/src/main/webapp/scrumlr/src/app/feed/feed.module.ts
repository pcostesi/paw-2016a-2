import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { FeedListComponent } from './feed-list/feed-list.component';
import { FeedDetailComponent } from './feed-detail/feed-detail.component';
import { FeedService } from 'app/feed/feed.service';
import { FeedUserCreatedComponent } from './feed-user-created/feed-user-created.component';
import { FeedProjectCreatedComponent } from './feed-project-created/feed-project-created.component';
import { FeedIterationCreatedComponent } from './feed-iteration-created/feed-iteration-created.component';
import { FeedStoryCreatedComponent } from './feed-story-created/feed-story-created.component';
import { FeedTaskCreatedComponent } from './feed-task-created/feed-task-created.component';
import { FeedBacklogItemCreatedComponent } from './feed-backlog-item-created/feed-backlog-item-created.component';
import { FeedUserListComponent } from './feed-user-list/feed-user-list.component';
import { FeedProjectListComponent } from './feed-project-list/feed-project-list.component';


@NgModule({
  imports: [
    CommonModule,
    NgbModule,
  ],
  declarations: [
    FeedListComponent, FeedDetailComponent, FeedUserCreatedComponent,
    FeedProjectCreatedComponent, FeedIterationCreatedComponent, FeedStoryCreatedComponent,
    FeedTaskCreatedComponent, FeedBacklogItemCreatedComponent, FeedUserListComponent, FeedProjectListComponent
  ],
  exports: [FeedListComponent, FeedProjectListComponent],
  providers: [FeedService]
})
export class FeedModule { }
