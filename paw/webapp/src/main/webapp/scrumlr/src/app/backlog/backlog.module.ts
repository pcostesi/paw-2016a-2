import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { MainComponent } from './main/main.component';
import { CompactComponent } from './compact/compact.component';
import { EditComponent } from './edit/edit.component';
import { CreateComponent } from './create/create.component';
import { DeleteComponent } from './delete/delete.component';

import { BacklogService } from './backlog.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
  ],
  declarations: [
    MainComponent,
    CompactComponent,
    EditComponent,
    CreateComponent,
    DeleteComponent
  ],
  exports: [MainComponent],
  entryComponents: [CreateComponent, EditComponent, DeleteComponent],
  providers: [BacklogService],
})
export class BacklogModule { }
