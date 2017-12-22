import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ProjectService } from '../project.service';
import { Observable } from 'rxjs/Observable';
import { Project } from 'app/project/project';
import { Iteration } from 'app/iteration/iteration';
import { IterationService } from 'app/iteration/iteration.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public project: Observable<Project>;
  public iterations: Iteration[];
  public codename: Observable<string>;
  private code: string;

  constructor(private route: ActivatedRoute,
    private iterationService: IterationService,
    private projectService: ProjectService) {
    const projectSummary = this.route.params.flatMap((params) =>
      this.projectService.getSummary(params['proj']));

    this.codename = this.route.params.map(params => params['proj']);
    this.project = projectSummary.map(result => result.project);
    projectSummary.map(result => result.iterations)
      .subscribe(iterations => this.iterations = iterations);
    this.project.subscribe(project => this.code = project.code);
  }

  ngOnInit() {
    this.iterationService.eventFeed.flatMap(event => {
      return this.iterationService.getIterations(this.code);
    }).subscribe(iterations => this.iterations = iterations);
  }
}
