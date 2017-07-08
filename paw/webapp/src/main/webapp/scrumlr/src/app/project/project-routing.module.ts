import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './main/main.component';
import { DetailComponent } from './detail/detail.component';

const routes: Routes = [
    { path: '', pathMatch: 'full', component: MainComponent },
    { path: ':proj', pathMatch: 'full', component: DetailComponent },
    { path: ':proj/iteration', loadChildren: '../iteration/iteration.module#IterationModule' },
    { path: ':proj/backlog', loadChildren: '../backlog/backlog.module#BacklogModule' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProjectRoutingModule { }
