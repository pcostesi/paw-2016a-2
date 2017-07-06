import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { ApiService, LoginEvent, AccountService } from '..';
import 'rxjs/Rx';

declare var $: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements AfterViewInit {
  @ViewChild('loginModal') modal: ElementRef;
  public username: string;
  public password: string;
  public loginError = false;

  constructor(private api: ApiService, private account: AccountService) { }

  ngAfterViewInit() {
    this.api.loginStatusTopic().subscribe(status => {
      switch (status) {
        case LoginEvent.BAD_CREDENTIALS:
          this.loginError = true;
          this.promptCredentials();
          break;

        case LoginEvent.REQUEST_CREDENTIALS:
          this.promptCredentials();
          break;
      }
    });

    this.account.stream.subscribe(user => user && this.closeModal());
  }

  public doLogin() {
    this.loginError = false;
    this.api.setCredentials(this.username, this.password);
  }

  public closeModal() {
    if ($(this.modal.nativeElement).hasClass('show')) {
      $(this.modal.nativeElement).modal('hide');
    }
  }

  public promptCredentials() {
    if (!$(this.modal.nativeElement).hasClass('show')) {
      $(this.modal.nativeElement).modal('show');
    }
  }

}
