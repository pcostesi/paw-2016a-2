import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AccountService } from '../../api';

@Component({
  selector: 'app-change-email',
  templateUrl: './change-email.component.html',
  styleUrls: ['./change-email.component.scss']
})
export class ChangeEmailComponent implements OnInit {
  public changeEmailForm: FormGroup;
  @Input() username: string;
  @Input() mail: string;

  constructor(private formBuilder: FormBuilder,
    private profileService: AccountService,
    private activeModal: NgbActiveModal) {
    this.changeEmailForm = formBuilder.group({
      email: ['user@example.com', Validators.compose([Validators.required, Validators.email])],
      email2: ['user@example.com', Validators.compose([Validators.required, Validators.email])],
    });
  }

  ngOnInit() {
    this.changeEmailForm.controls['email'].setValue(this.mail);
    this.changeEmailForm.controls['email2'].setValue(this.mail);
  }

  onSubmit(form: any) {
    this.profileService.updateProfile(this.username, this.mail)
      .subscribe(success => {
        this.activeModal.close();
      });
  }

}
