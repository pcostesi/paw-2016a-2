import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'scrumlr-project-summary',
  templateUrl: './project-summary.component.html',
  styleUrls: ['./project-summary.component.scss']
})
export class ProjectSummaryComponent implements OnInit {
  @Input() project: any;

  constructor() { }

  ngOnInit() {
  }

}
