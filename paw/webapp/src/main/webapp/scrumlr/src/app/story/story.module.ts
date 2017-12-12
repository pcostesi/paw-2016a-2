import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StoryRoutingModule } from './story-routing.module';
import { StoryListComponent } from './story-list/story-list.component';
import { StoryService } from './story.service';
import { StoryDetailComponent } from './story-detail/story-detail.component';
import { TaskModule } from '../task/task.module';
import { StoryEditComponent } from './story-edit/story-edit.component';
import { StoryCreateComponent } from './story-create/story-create.component';
import { StoryDeleteComponent } from './story-delete/story-delete.component';

@NgModule({
  imports: [
    CommonModule,
    StoryRoutingModule,
    TaskModule
  ],
  providers: [StoryService],
  declarations: [StoryListComponent, StoryDetailComponent, StoryEditComponent, StoryCreateComponent, StoryDeleteComponent],
  exports: [StoryListComponent]
})
export class StoryModule { }
