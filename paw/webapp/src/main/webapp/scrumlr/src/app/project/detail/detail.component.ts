import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ProjectService } from '../project.service';


@Component({
  selector: 'scrumlr-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public project: any;
  private code: string;

  constructor(private route: ActivatedRoute,
              private projectService: ProjectService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.code = params['proj'];
      this.fetchProject(this.code);
    });
  }


  fetchProject(code: string) {
    this.projectService.getProject(code).subscribe(result => {
      this.project = result;
    });
  }
}
