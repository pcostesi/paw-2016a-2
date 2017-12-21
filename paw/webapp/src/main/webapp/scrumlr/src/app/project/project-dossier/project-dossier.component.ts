import { Component, OnInit, Input } from '@angular/core';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ProjectDeleteComponent } from '../project-delete/project-delete.component';
import { Project } from '../project';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project-dossier',
  templateUrl: './project-dossier.component.html',
  styleUrls: ['./project-dossier.component.scss']
})
export class ProjectDossierComponent implements OnInit {
  @Input() project: Project;
  constructor(private modalService: NgbModal,
    public router: Router) { }

  ngOnInit() {
  }

  deleteProject() {
    const ref = this.modalService.open(ProjectDeleteComponent);
    ref.componentInstance.project = this.project;
    ref.result.then(result => {
      this.router.navigate(['project']);
    })
  }

}
