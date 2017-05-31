import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AccountService, ApiService, LoginEvent, MaybeUser } from '..';

@Component({
  selector: 'app-badge',
  templateUrl: './badge.component.html',
  styleUrls: ['./badge.component.scss'],
})
export class BadgeComponent implements OnInit {
  private hasCredentials = false;
  private user: MaybeUser;

  constructor(private accountService: AccountService,
              private api: ApiService) { }

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
  }

  private logOut() {
    this.api.clearCredentials();
  }
  private logIn() {
    this.api.requestCredentials();
  }

}
