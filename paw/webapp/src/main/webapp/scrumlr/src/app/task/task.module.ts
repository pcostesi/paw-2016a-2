import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TaskService } from './task.service';
import { TaskRoutingModule } from './task-routing.module';
import { TaskListComponent } from './task-list/task-list.component';
import { TaskDetailComponent } from './task-detail/task-detail.component';
import { TaskEditComponent } from './task-edit/task-edit.component';
import { TaskCreateComponent } from './task-create/task-create.component';
import { TaskDeleteComponent } from './task-delete/task-delete.component';

@NgModule({
  imports: [
    CommonModule,
    TaskRoutingModule
  ],
  providers: [TaskService],
  declarations: [TaskListComponent, TaskDetailComponent, TaskEditComponent, TaskCreateComponent, TaskDeleteComponent],
  exports: [TaskListComponent]
})
export class TaskModule { }
