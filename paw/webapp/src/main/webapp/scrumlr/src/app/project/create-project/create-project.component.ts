import { Component, OnInit } from '@angular/core';
import {
  FormBuilder, FormGroup, Validators,
  FormControl, FormArray, AbstractControl
} from '@angular/forms';

import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operator/map';
import { debounceTime } from 'rxjs/operator/debounceTime';
import { distinctUntilChanged } from 'rxjs/operator/distinctUntilChanged';

import { AccountService } from '../../api';

import { ProjectService } from '../project.service'
import { Project } from '../project';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.scss']
})
export class CreateProjectComponent implements OnInit {
  public projectForm: FormGroup;
  public users: string[];

  constructor(private formBuilder: FormBuilder,
              private accountService: AccountService,
              private router: Router,
              private projectService: ProjectService) {
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
    const eventStream = distinctUntilChanged.call(debounceTime.call(text, 200));
    return map.call(eventStream, (term: string) => {
      return term.length < 2 ? [] : this.users.filter(v => v.toLowerCase()
        .startsWith(term.toLocaleLowerCase()))
        .splice(0, 10);
    });
  }

  getUserControls() {
    const usersControl = <FormArray>this.projectForm.controls.users;
    return usersControl.controls;
  }

  onSubmit(projectForm: FormGroup) {
    console.log(projectForm);
    if (projectForm.valid) {
      const project = new Project();
      project.code = projectForm.controls.codename.value;
      project.description = projectForm.controls.description.value;
      project.name = projectForm.controls.title.value;
      project.members = projectForm.controls.users.value;
      this.projectService.createProject(project)
        .subscribe(ok => {
          if (ok) {
            this.router.navigate(['/project', project.code])
          }
        });
    }
  }

  ngOnInit() {
    this.accountService.getUsers().subscribe(users => {
      this.users = users;
    });
  }

}
