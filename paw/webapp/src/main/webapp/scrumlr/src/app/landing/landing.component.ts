import { Component, OnInit } from '@angular/core';
import { ApiService, AccountService, UserProfile } from '../api';

@Component({
  selector: 'scrumlr-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {

  public user: UserProfile | null;

  constructor(private api: ApiService, private acctsrv: AccountService) { }

  ngOnInit() {
    this.acctsrv.stream.subscribe(user => {
      this.user = user;
    });
    this.user = this.acctsrv.getLoggedAccount();
  }

  public doLogin() {
    this.api.requestCredentials();
  }

}
