import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ProjectService } from '../project.service';
import { Observable } from 'rxjs/Observable';
import { Project } from 'app/project/project';
import { Iteration } from 'app/iteration/iteration';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public project: Observable<Project>;
  public iterations: Observable<Iteration[]>;
  public codename: Observable<string>;

  constructor(private route: ActivatedRoute,
    private projectService: ProjectService) {
    const projectSummary = this.route.params.flatMap((params) =>
      this.projectService.getSummary(params['proj']));

    this.codename = this.route.params.map(params => params['proj']);
    this.project = projectSummary.map(result => result.project);
    this.iterations = projectSummary.map(result => result.iterations);
  }

  ngOnInit() {
  }
}
