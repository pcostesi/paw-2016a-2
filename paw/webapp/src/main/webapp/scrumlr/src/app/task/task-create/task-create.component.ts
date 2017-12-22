import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { UserProfile } from 'app/api';
import {TaskService} from '../task.service';
import {Task} from '../task';
import {Router} from '@angular/router';


@Component({
  selector: 'app-task-create',
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.scss']
})
export class TaskCreateComponent implements OnInit {
  public form: FormGroup;
  @Input() story: any;

  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal,
    private taskService: TaskService,
    private router: Router) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      title: [undefined, Validators.required],
      description: [undefined],
      status: ['NOT_STARTED', Validators.required],
      priority: ['NORMAL', Validators.required],
      score: ['NORMAL', Validators.required],
      owner: [undefined],
    });
  }

  onSubmit(form: FormGroup) {
    console.log(form);
    const task = new Task();
    task.title = form.controls.title.value;
    task.description = form.controls.description.value;
    task.priority = form.controls.priority.value;
    task.score = form.controls.score.value;
    task.owner = form.controls.owner.value.username;
    task.status = form.controls.status.value;
    this.taskService.createTask(this.story.iteration.project.code,
      this.story.iteration.number, this.story.storyId, task).subscribe(ok => {
      if (ok) {
        this.router.navigate(['/project', this.story.iteration.project.code])
      }
    });
    this.modal.close(form);
  }

  search = (text$: Observable<string>) => {
    const users = <UserProfile[]>this.story.iteration.project.members;
    return text$
      .debounceTime(200)
      .map(term => term === '' ? []
        : users.filter(v => v.username.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10));
  }

  formatter = (x: UserProfile) => x.username;


}
