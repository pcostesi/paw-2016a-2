import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'scrumlr-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.scss']
})
export class SummaryComponent implements OnInit {

  @Input() project: any;

  constructor() { }

  ngOnInit() {
  }

}
