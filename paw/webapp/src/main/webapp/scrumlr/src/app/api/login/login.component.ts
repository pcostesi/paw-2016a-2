import {
  Component,
  OnInit,
  AfterViewInit,
  ViewChild,
  ElementRef
} from '@angular/core';
import { ApiService } from '../api.service';
import { LoginEvent } from '../login-event.enum';
import { AccountService } from '../account.service';
import 'rxjs/Rx';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements AfterViewInit {
  @ViewChild('loginModal') modal: ElementRef;
  public username: string;
  public password: string;
  public loginError = false;

  constructor(
    private api: ApiService,
    private account: AccountService,
    private activeModal: NgbActiveModal
  ) { }

  ngAfterViewInit() {
    this.api.loginStatusTopic().subscribe(status => {
      this.loginError = status === LoginEvent.BAD_CREDENTIALS;
    });

    this.account.stream.subscribe(user => user && this.closeModal());
  }

  public doLogin() {
    this.loginError = false;
    this.api.setCredentials(this.username, this.password);
  }

  public closeModal() {
    this.activeModal.close();
  }
}
