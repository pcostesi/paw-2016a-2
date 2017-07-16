import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { IterationRoutingModule } from './iteration-routing.module';
import { StoryModule } from '../story/story.module';
import { IterationService } from './iteration.service';
import { IterationListComponent } from './iteration-list/iteration-list.component';
import { IterationDetailComponent } from './iteration-detail/iteration-detail.component';

@NgModule({
  imports: [
    CommonModule,
    IterationRoutingModule,
    NgbModule,
    StoryModule
  ],
  declarations: [IterationListComponent, IterationDetailComponent],
  providers: [IterationService],
  exports: [IterationListComponent]
})
export class IterationModule { }
