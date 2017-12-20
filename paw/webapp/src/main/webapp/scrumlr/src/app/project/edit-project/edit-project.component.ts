import { Component, OnInit } from '@angular/core';
import {
  FormBuilder, FormGroup, Validators,
  FormControl, FormArray, AbstractControl
} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operator/map';
import { debounceTime } from 'rxjs/operator/debounceTime';
import { distinctUntilChanged } from 'rxjs/operator/distinctUntilChanged';

import { AccountService, UserProfile } from '../../api';
import { ProjectService } from '../project.service';
import { Project } from 'app/project/project';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.scss']
})

export class EditProjectComponent implements OnInit {
  public projectForm: FormGroup;
  public project: Project;
  public users: string[];

  constructor(private formBuilder: FormBuilder,
    private router: ActivatedRoute,
    private projectService: ProjectService,
    private accountService: AccountService) {
    this.projectForm = this.buildProjectForm();
  }

  buildProjectForm(): FormGroup {
    return this.formBuilder.group({
      title: ['Scrumlr - The Awesome Scrum Manager', Validators.required],
      description: ['A scrum management software.', Validators.maxLength(140)],
      users: this.formBuilder.array([this.buildMemberForm()])
    });
  }

  buildMemberForm(memberDetails?: any): FormControl {
    let username: string;
    if (memberDetails && memberDetails.username) {
      username = memberDetails.username;
    } else {
      const profile = this.accountService.getLoggedAccount();
      username = profile ? profile.username : '';
    }
    return this.formBuilder.control(username, Validators.compose([Validators.required, Validators.pattern(/\w+/)]));
  }

  addMember(memberDetails?: UserProfile) {
    const usersControl = <FormArray>this.projectForm.controls.users;
    usersControl.push(this.buildMemberForm(memberDetails));
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

  onSubmit(form: any) {

  }

  updateProjectDetails(code: string) {
    const form = this.projectForm;
    this.projectService.getProject(code).subscribe(project => {
      this.project = project;
      const users = project.members.map((u: UserProfile) => this.buildMemberForm(u));
      form.controls.title.setValue(project.name);
      form.controls.description.setValue(project.description);
      form.controls.users = this.formBuilder.array(users);
    });
  }

  ngOnInit() {
    this.router.params.subscribe(params => {
      const projectCode = params['proj'];
      this.updateProjectDetails(projectCode);
    });
    this.accountService.getUsers().subscribe(users => {
      this.users = users;
    });
  }

}
