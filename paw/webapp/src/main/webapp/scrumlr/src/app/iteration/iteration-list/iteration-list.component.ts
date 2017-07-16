import { Component, OnInit, Input } from '@angular/core';

import { NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'scrumlr-iteration-list',
  templateUrl: './iteration-list.component.html',
  styleUrls: ['./iteration-list.component.scss']
})
export class IterationListComponent implements OnInit {
  @Input() iterations: any[];

  constructor() { }

  onPanelSelect(evt: NgbPanelChangeEvent) {
  }


  ngOnInit() {
  }

}
