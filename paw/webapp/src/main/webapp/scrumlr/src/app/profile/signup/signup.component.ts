import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AccountService } from '../../api';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  public signupForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private acctsrv: AccountService) {
    this.signupForm = formBuilder.group({
      email: ['user@example.com', Validators.compose([Validators.required, Validators.email])],
      username: ['user', Validators.required],
      password: ['zekrit', Validators.required]
    });
  }

  ngOnInit() {
  }

  onSubmit(form: any): void {
    if (this.signupForm.valid) {
      console.log(form);
      this.acctsrv.signupUser(form.username, form.password, form.email).subscribe(res => {console.log(res)});
      //this.router.navigate(['/']);
    }
  }

}
