import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IterationRoutingModule } from './iteration-routing.module';
import { IterationService } from './iteration.service';

@NgModule({
  imports: [
    CommonModule,
    IterationRoutingModule
  ],
  declarations: [],
  providers: [IterationService]
})
export class IterationModule { }
