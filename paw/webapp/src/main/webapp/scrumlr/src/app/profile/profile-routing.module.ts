import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DetailComponent } from './detail/detail.component';
import { LoginGuard } from '../api';

const routes: Routes = [
  { path: '',  redirectTo: 'me', pathMatch: 'full' },
  { path: 'me', component: DetailComponent, canActivate: [LoginGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
