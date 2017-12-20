import { Component, OnInit } from '@angular/core';
import { BadgeComponent, AccountService } from '../api';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  public isCollapsed = true;
  public isLoggedIn = false;
  constructor(private acctsrv: AccountService) { }

  ngOnInit() {
    this.acctsrv.stream.subscribe(user => this.isLoggedIn = !!user);
    this.isLoggedIn = !!this.acctsrv.getLoggedAccount();
  }

}
