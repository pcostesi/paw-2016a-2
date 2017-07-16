import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TaskService } from './task.service';
import { TaskRoutingModule } from './task-routing.module';

@NgModule({
  imports: [
    CommonModule,
    TaskRoutingModule
  ],
  providers: [TaskService],
  declarations: [],
  exports: []
})
export class TaskModule { }
