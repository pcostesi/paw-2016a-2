import { Component, OnInit, Input } from '@angular/core';

import { NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { Iteration } from 'app/iteration/iteration';

@Component({
  selector: 'app-iteration-list',
  templateUrl: './iteration-list.component.html',
  styleUrls: ['./iteration-list.component.scss']
})
export class IterationListComponent implements OnInit {
  public page = 0;
  private _iterations: Iteration[];

  constructor() { }

  onPanelSelect(evt: NgbPanelChangeEvent) {
  }

  ngOnInit() {
  }

  @Input()
  set iterations(value: Iteration[]) {
    if (!value) {
      this.page = 0;
      this._iterations = [];
      return;
    }
    this.page = value.length || 0;
    this._iterations = value;
  }

  get iterations() {
    return this._iterations;
  }

}
