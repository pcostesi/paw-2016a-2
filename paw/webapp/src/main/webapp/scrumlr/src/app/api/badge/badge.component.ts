import { Component, OnInit } from '@angular/core';
import { ApiService, LoginEvent } from '..';

@Component({
  selector: 'app-badge',
  templateUrl: './badge.component.html',
  styleUrls: ['./badge.component.scss'],

})
export class BadgeComponent implements OnInit {
  private hasCredentials = false;
  private username: string;

  constructor(private api: ApiService) { }

  ngOnInit() {
    this.hasCredentials = this.api.hasCredentialsSet();
    this.api.loginStatusTopic().subscribe(status => {
      switch (status) {
        case LoginEvent.BAD_CREDENTIALS:
          this.hasCredentials = false;
          break;

        case LoginEvent.SET_CREDENTIALS:
          this.verifyCredentials();
          break;

        case LoginEvent.CLEARED_CREDENTIALS:
          this.hasCredentials = false;
          break;
      }
    });
  }

  private logOut() {
    this.api.clearCredentials();
  }

  private verifyCredentials() {
    this.api.get('/user/me').subscribe(success => {
      this.hasCredentials = true;
      this.username = success['username'];

    }, error => {
      if (error.status === 401) {
        this.hasCredentials = false;
      }
    });
  }

}
