import { Component, OnInit, Input } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Project } from '../project';
import { ProjectService } from '../project.service';


@Component({
  selector: 'app-project-delete',
  templateUrl: './project-delete.component.html',
  styleUrls: ['./project-delete.component.scss']
})
export class ProjectDeleteComponent implements OnInit {
  @Input() project: Project;

  constructor(public projectService: ProjectService,
    public modal: NgbActiveModal) { }

  ngOnInit() {
  }

  deleteProject() {
    this.projectService.deleteProject(this.project)
      .subscribe(ok => {
        if (ok) {
          this.modal.close(ok);
        }
      });
  }
}
