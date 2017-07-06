import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
    { path: ':proj/iteration', loadChildren: '../iteration/iteration.module#IterationModule' },
    { path: ':proj/backlog', loadChildren: '../backlog/backlog.module#BacklogModule' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProjectRoutingModule { }
