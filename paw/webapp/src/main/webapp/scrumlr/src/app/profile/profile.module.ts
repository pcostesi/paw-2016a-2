import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { ProfileRoutingModule } from './profile-routing.module';
import { DetailComponent } from './detail/detail.component';
import { SignupComponent } from './signup/signup.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ProjectSummaryComponent } from './project-summary/project-summary.component';
import { WorkItemComponent } from './work-item/work-item.component';
import { ChangeEmailComponent } from './change-email/change-email.component';

@NgModule({
  imports: [
    CommonModule,
    ProfileRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
  ],
  entryComponents: [ ChangeEmailComponent, ChangePasswordComponent ],
  declarations: [
    DetailComponent,
    SignupComponent,
    ChangePasswordComponent,
    ProjectSummaryComponent,
    WorkItemComponent,
    ChangeEmailComponent
  ],
})
export class ProfileModule { }
