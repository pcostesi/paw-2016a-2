import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ApiService, AccountService } from '../../api';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
  @Input() username: string;
  @Input() mail: string;
  public changePassForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private profileService: AccountService,
              private apiService: ApiService,
              private activeModal: NgbActiveModal) {
     this.changePassForm = formBuilder.group({
      password: ['user@example.com', Validators.required],
      password2: ['user@example.com', Validators.required],
    });
  }

  ngOnInit() {
  }

  onSubmit(form: any) {
    const pass1: string = form.password;
    const pass2: string = form.password2;
    if (pass1 != pass2) { return; }
    this.profileService.updateProfile(this.username, this.mail, pass1)
      .subscribe(success => {
        this.activeModal.close();
        this.apiService.requestCredentials();
      });
  }
}
