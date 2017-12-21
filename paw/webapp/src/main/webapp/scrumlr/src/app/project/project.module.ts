import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { BacklogModule } from '../backlog/backlog.module';
import { IterationModule } from '../iteration/iteration.module';
import { ProjectRoutingModule } from './project-routing.module';
import { MainComponent } from './main/main.component';
import { DetailComponent } from './detail/detail.component';
import { SummaryComponent } from './summary/summary.component';
import { ProjectService } from './project.service';
import { CreateProjectComponent } from './create-project/create-project.component';
import { EditProjectComponent } from './edit-project/edit-project.component';
import { ProjectDossierComponent } from './project-dossier/project-dossier.component';
import { FeedModule } from 'app/feed/feed.module';
import { ProjectDeleteComponent } from './project-delete/project-delete.component';

@NgModule({
  imports: [
    CommonModule,
    ProjectRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    BacklogModule,
    IterationModule,
    FeedModule,
  ],
  declarations: [MainComponent, DetailComponent, SummaryComponent, CreateProjectComponent,
    EditProjectComponent, ProjectDossierComponent, ProjectDeleteComponent],
  providers: [ProjectService],
  entryComponents: [ProjectDeleteComponent],
})
export class ProjectModule { }
