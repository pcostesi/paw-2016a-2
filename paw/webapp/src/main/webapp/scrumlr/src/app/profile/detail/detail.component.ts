import { Component, OnInit } from '@angular/core';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ChangePasswordComponent } from '../change-password/change-password.component';
import { ChangeEmailComponent } from '../change-email/change-email.component';
import { AccountService } from '../../api';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public username: string;
  public mail: string;
  public projects: string[];
  public workItems: string[];

  constructor(private modalService: NgbModal,
              private profileService: AccountService) { }

  ngOnInit() {
    const user = this.profileService.getLoggedAccount();
    if (user) {
      this.username = user.username;
      this.mail = user.mail;
    }
  }

  private promptModal(component: any) {
    const modal = this.modalService.open(component);
    modal.componentInstance.username = this.username;
    modal.componentInstance.mail = this.mail;
  }

  changePassword() {
    return this.promptModal(ChangePasswordComponent);
  }

  changeEmail() {
    return this.promptModal(ChangeEmailComponent);
  }

}
