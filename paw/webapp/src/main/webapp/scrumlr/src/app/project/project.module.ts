import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectRoutingModule } from './project-routing.module';
import { MainComponent } from './main/main.component';
import { DetailComponent } from './detail/detail.component';
import { SummaryComponent } from './summary/summary.component';
import { ProjectService } from './project.service';

@NgModule({
  imports: [
    CommonModule,
    ProjectRoutingModule
  ],
  declarations: [MainComponent, DetailComponent, SummaryComponent],
  providers: [ ProjectService ],
})
export class ProjectModule { }
