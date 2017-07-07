import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.scss']
})
export class SummaryComponent implements OnInit {

  public project = {
    name: 'Scrumlr',
    code: 'scrumlr',
    description: 'The best project ever'
  };

  constructor() { }

  ngOnInit() {
  }

}
