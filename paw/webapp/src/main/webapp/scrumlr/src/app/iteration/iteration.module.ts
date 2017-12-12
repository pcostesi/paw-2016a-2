import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { IterationRoutingModule } from './iteration-routing.module';
import { StoryModule } from '../story/story.module';
import { IterationService } from './iteration.service';
import { IterationListComponent } from './iteration-list/iteration-list.component';
import { IterationDetailComponent } from './iteration-detail/iteration-detail.component';
import { IterationCreateComponent } from './iteration-create/iteration-create.component';
import { IterationEditComponent } from './iteration-edit/iteration-edit.component';
import { IterationDeleteComponent } from './iteration-delete/iteration-delete.component';

@NgModule({
  imports: [
    CommonModule,
    IterationRoutingModule,
    NgbModule,
    StoryModule
  ],
  declarations: [
    IterationListComponent,
    IterationDetailComponent,
    IterationCreateComponent,
    IterationEditComponent,
    IterationDeleteComponent
  ],
  providers: [IterationService],
  exports: [IterationListComponent]
})
export class IterationModule { }
