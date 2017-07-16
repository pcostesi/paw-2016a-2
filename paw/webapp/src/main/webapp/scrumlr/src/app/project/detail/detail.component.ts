import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';

import { ProjectService } from '../project.service';


@Component({
  selector: 'scrumlr-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public project: any;
  public backlog: any[];
  public iterations: any[];
  private code: string;

  constructor(private route: ActivatedRoute,
    private projectService: ProjectService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.code = params['proj'];
      this.fetchProject(this.code);
    });
  }

  onPanelSelect(evt: NgbPanelChangeEvent) {
    console.log(evt);
  }

  fetchProject(code: string) {
    this.projectService.getSummary(code).subscribe(result => {
      this.project = result.project;
      this.backlog = result.backlog;
      this.iterations = result.iterations;
    });
  }
}
