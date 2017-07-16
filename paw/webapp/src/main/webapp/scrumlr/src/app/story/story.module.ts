import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StoryRoutingModule } from './story-routing.module';
import { StoryListComponent } from './story-list/story-list.component';
import { StoryService } from './story.service';

@NgModule({
  imports: [
    CommonModule,
    StoryRoutingModule
  ],
  providers: [StoryService],
  declarations: [StoryListComponent],
  exports: [StoryListComponent]
})
export class StoryModule { }
