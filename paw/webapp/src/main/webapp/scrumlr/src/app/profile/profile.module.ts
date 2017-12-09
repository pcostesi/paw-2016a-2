import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


import { IterationModule } from '../iteration/iteration.module';
import { ProfileRoutingModule } from './profile-routing.module';
import { DetailComponent } from './detail/detail.component';
import { SignupComponent } from './signup/signup.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ProjectSummaryComponent } from './project-summary/project-summary.component';
import { WorkItemComponent } from './work-item/work-item.component';
import { ChangeEmailComponent } from './change-email/change-email.component';
import { UserProfileComponent } from './user-profile/user-profile.component';

@NgModule({
  imports: [
    CommonModule,
    ProfileRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    IterationModule
  ],
  entryComponents: [ChangeEmailComponent, ChangePasswordComponent],
  declarations: [
    DetailComponent,
    SignupComponent,
    ChangePasswordComponent,
    ProjectSummaryComponent,
    WorkItemComponent,
    ChangeEmailComponent,
    UserProfileComponent
  ],
})
export class ProfileModule { }
