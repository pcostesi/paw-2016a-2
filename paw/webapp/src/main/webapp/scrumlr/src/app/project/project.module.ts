import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { BacklogModule } from '../backlog/backlog.module';
import { ProjectRoutingModule } from './project-routing.module';
import { MainComponent } from './main/main.component';
import { DetailComponent } from './detail/detail.component';
import { SummaryComponent } from './summary/summary.component';
import { ProjectService } from './project.service';
import { CreateProjectComponent } from './create-project/create-project.component';
import { EditProjectComponent } from './edit-project/edit-project.component';

@NgModule({
  imports: [
    CommonModule,
    ProjectRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    BacklogModule,
  ],
  declarations: [MainComponent, DetailComponent, SummaryComponent, CreateProjectComponent, EditProjectComponent],
  providers: [ ProjectService ],
})
export class ProjectModule { }
