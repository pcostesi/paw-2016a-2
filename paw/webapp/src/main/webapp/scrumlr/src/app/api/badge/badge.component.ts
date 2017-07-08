import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';

import { AccountService, ApiService, LoginEvent, MaybeUser, LoginComponent } from '..';

const modalOptions: NgbModalOptions = {
  windowClass: 'fade',
  size: 'sm'
};

@Component({
  selector: 'app-badge',
  templateUrl: './badge.component.html',
  styleUrls: ['./badge.component.scss'],
})
export class BadgeComponent implements OnInit {
  public hasCredentials = false;
  public user: MaybeUser;

  constructor(private accountService: AccountService,
              private api: ApiService,
              private router: Router,
              private modalService: NgbModal) { }

  ngOnInit() {
    this.hasCredentials = this.accountService.getLoggedAccount() != null;
    this.accountService.stream.subscribe(
      result => {
        this.user = result;
        this.hasCredentials = result != null;
      },
      error => {
        this.user = null;
        this.hasCredentials = false;
      });

    this.api.loginStatusTopic().subscribe(status => {
      switch (status) {
        case LoginEvent.BAD_CREDENTIALS:
          this.modalService.open(LoginComponent, modalOptions);
          break;

        case LoginEvent.REQUEST_CREDENTIALS:
          this.modalService.open(LoginComponent, modalOptions);
          break;
      }
    });
  }

  public logOut() {
    this.api.clearCredentials();
    this.router.navigate(['/']);
  }

  public logIn() {
    this.api.requestCredentials();
  }

}
