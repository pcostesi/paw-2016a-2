import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Task } from 'app/task/task';
import { TaskStatus } from 'app/task/task-status.enum';
import { TaskPriority } from 'app/task/task-priority.enum';
import { TaskScore } from 'app/task/task-score.enum';
import { UserProfile } from 'app/api/account.service';
import { Observable } from 'rxjs/Observable';
import {TaskService} from '../task.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-task-edit',
  templateUrl: './task-edit.component.html',
  styleUrls: ['./task-edit.component.scss']
})
export class TaskEditComponent implements OnInit {
  public form: FormGroup;
  @Input() task: Task;

  constructor(private formBuilder: FormBuilder,
    public modal: NgbActiveModal,
    private taskService: TaskService,
    private router: Router) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      title: [this.task.title, Validators.required],
      description: [this.task.description],
      status: [this.task.status, Validators.required],
      priority: [this.task.priority, Validators.required],
      score: [this.task.score, Validators.required],
      owner: [this.task.owner ? this.task.owner : undefined],
    });
  }

  onSubmit(form: FormGroup) {
    const editedTask = new Task();
    editedTask.title = form.controls.title.value;
    editedTask.description = form.controls.description.value;
    editedTask.priority = form.controls.priority.value;
    editedTask.score = form.controls.score.value;
    editedTask.owner = form.controls.owner.value.username;
    editedTask.status = form.controls.status.value;
    this.taskService.updateTask(this.task.story.iteration.project.code,
      this.task.story.iteration.number, this.task.story.storyId, this.task.taskId, editedTask).subscribe(ok => {
      if (ok) {
        this.router.navigate(['/project', this.task.story.iteration.project.code])
      }
    });
    this.modal.close(form);
  }

  search = (text$: Observable<string>) => {
    const users = <UserProfile[]>this.task.story.iteration.project.members;
    return text$
      .debounceTime(200)
      .map(term => term === '' ? []
        : users.filter(v => v.username.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10));
  }

  formatter = (x: UserProfile) => x.username;

}
