import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DetailComponent } from './detail/detail.component';
import { SignupComponent } from './signup/signup.component';
import { LoginGuard } from '../api';

const routes: Routes = [
  { path: '',  redirectTo: 'me', pathMatch: 'full' },
  { path: 'me', component: DetailComponent, canActivate: [LoginGuard] },
  { path: 'signup', component: SignupComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
