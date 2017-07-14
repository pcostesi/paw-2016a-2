import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BacklogRoutingModule } from './backlog-routing.module';
import { MainComponent } from './main/main.component';
import { DetailComponent } from './detail/detail.component';
import { CompactComponent } from './compact/compact.component';
import { EditComponent } from './edit/edit.component';
import { CreateComponent } from './create/create.component';
import { DeleteComponent } from './delete/delete.component';

@NgModule({
  imports: [
    CommonModule,
    BacklogRoutingModule
  ],
  declarations: [MainComponent, DetailComponent, CompactComponent, EditComponent, CreateComponent, DeleteComponent]
})
export class BacklogModule { }
