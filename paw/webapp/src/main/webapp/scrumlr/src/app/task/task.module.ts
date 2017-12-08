import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TaskService } from './task.service';
import { TaskRoutingModule } from './task-routing.module';
import { TaskListComponent } from './task-list/task-list.component';
import { TaskDetailComponent } from './task-detail/task-detail.component';

@NgModule({
  imports: [
    CommonModule,
    TaskRoutingModule
  ],
  providers: [TaskService],
  declarations: [TaskListComponent, TaskDetailComponent],
  exports: [TaskListComponent]
})
export class TaskModule { }
