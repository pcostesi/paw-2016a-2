import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MainComponent } from './main/main.component';
import { DetailComponent } from './detail/detail.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', component: MainComponent },
  { path: ':id', pathMatch: 'full', component: DetailComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BacklogRoutingModule { }
