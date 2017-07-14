import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators,
  FormControl, FormArray, AbstractControl } from '@angular/forms';

import {Observable} from 'rxjs/Observable';
import {map} from 'rxjs/operator/map';
import {debounceTime} from 'rxjs/operator/debounceTime';
import {distinctUntilChanged} from 'rxjs/operator/distinctUntilChanged';

import { AccountService } from '../../api';

@Component({
  selector: 'scrumlr-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.scss']
})
export class CreateProjectComponent implements OnInit {
  public projectForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private accountService: AccountService) {
    this.projectForm = this.buildProjectForm();
  }

  buildProjectForm(): FormGroup {
    return this.formBuilder.group({
      codename: ['scrumlr', Validators.compose([Validators.required, Validators.pattern(/\w+/)])],
      title: ['Scrumlr - The Awesome Scrum Manager', Validators.required],
      description: ['A scrum management software.', Validators.maxLength(140)],
      users: this.formBuilder.array([this.buildMemberForm()])
    });
  }

  buildMemberForm(): FormControl {
    const profile = this.accountService.getLoggedAccount();
    const username = profile ? profile.username : '';
    return this.formBuilder.control(username, Validators.compose([Validators.required, Validators.pattern(/\w+/)]));
  }

  addMember() {
    const usersControl = <FormArray>this.projectForm.controls.users;
    usersControl.push(this.buildMemberForm());
  }

  removeMember(i: number) {
    const usersControl = <FormArray>this.projectForm.controls.users;
    usersControl.removeAt(i);
  }

  getUsers(text: Observable<string>) {
    const users = ['pablo', 'braulio', 'maio'];
    const eventStream = distinctUntilChanged.call(debounceTime.call(text, 200));
    return map.call(eventStream, (term: string) => {
      return term.length < 2 ? [] : users.filter(v => v.toLowerCase()
        .startsWith(term.toLocaleLowerCase()))
        .splice(0, 10);
    });
  }

  getUserControls() {
    const usersControl = <FormArray>this.projectForm.controls.users;
    return usersControl.controls;
  }

  onSubmit(form: any) {

  }

  ngOnInit() {
    //example
    //this.accountService.getUsers().subscribe(users => console.log(users));
  }

}
