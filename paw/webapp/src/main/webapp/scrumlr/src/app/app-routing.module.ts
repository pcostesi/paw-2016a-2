import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SampleComponent } from './sample/sample.component';

const routes: Routes = [
  { path: 'sample', component: SampleComponent },
  { path: 'profile', loadChildren: './profile/profile.module#ProfileModule' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule { }

