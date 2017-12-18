import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ApiService, AccountService, UserProfile } from '../api';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {
  @ViewChild('landingBanner') jumbotron: ElementRef;

  public user: UserProfile | null;

  constructor(private api: ApiService, private acctsrv: AccountService) { }

  ngOnInit() {
    this.acctsrv.stream.subscribe(user => {
      this.user = user;
    });
    this.user = this.acctsrv.getLoggedAccount();
    Observable.from(['landing-banner-real']).delay(10000).subscribe(cls => {
      console.log(cls)
      this.jumbotron.nativeElement.classList.add(cls);
    })
  }

  public doLogin() {
    this.api.requestCredentials();
  }

}
