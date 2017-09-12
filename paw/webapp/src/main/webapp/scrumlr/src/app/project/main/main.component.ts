import { Component, OnInit } from '@angular/core';

import { ProjectService } from '../project.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  public projects = [];
  constructor(private projectService: ProjectService) { }

  ngOnInit() {
    this.projectService.getProjects().subscribe(response => {
      this.projects = response.projects;
    });
  }

}
