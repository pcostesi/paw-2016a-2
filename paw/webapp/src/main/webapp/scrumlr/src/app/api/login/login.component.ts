import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { ApiService, LoginEvent } from '..';
import 'rxjs/Rx';

declare var $: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements AfterViewInit {
  @ViewChild('loginModal') modal: ElementRef;
  private username: string;
  private password: string;
  private loginError = false;

  constructor(private api: ApiService) { }

  ngAfterViewInit() {
    this.api.loginStatusTopic().subscribe(status => {
      switch (status) {
        case LoginEvent.BAD_CREDENTIALS:
          this.promptCredentials();
          break;

        case LoginEvent.SET_CREDENTIALS:
          this.verifyCredentials();
          break;
      }
    });
  }

  private doLogin() {
    this.api.setCredentials(this.username, this.password);
  }

  private promptCredentials() {
    if (!$(this.modal.nativeElement).hasClass('show')) {
      $(this.modal.nativeElement).modal('show');
    }
  }


  private verifyCredentials() {
    this.username = this.password = '';
    this.api.get('/user/me').subscribe(success => {
      this.loginError = false;
      if ($(this.modal.nativeElement).hasClass('show')) {
        $(this.modal.nativeElement).modal('hide');
      }
    }, error => {
      if (error.status === 401) {
        this.loginError = true;
      }
    });
  }

}
