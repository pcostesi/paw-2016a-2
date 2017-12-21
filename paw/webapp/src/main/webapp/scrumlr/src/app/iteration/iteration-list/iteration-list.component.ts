import { Component, OnInit, Input } from '@angular/core';

import { NgbPanelChangeEvent, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Iteration } from 'app/iteration/iteration';
import { Project } from 'app/project/project';
import { IterationCreateComponent } from 'app/iteration/iteration-create/iteration-create.component';

@Component({
  selector: 'app-iteration-list',
  templateUrl: './iteration-list.component.html',
  styleUrls: ['./iteration-list.component.scss']
})
export class IterationListComponent implements OnInit {
  public page = 0;
  @Input() project: Project;
  private _iterations: Iteration[];

  constructor(private modalService: NgbModal) { }

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

  newIteration(project: Project) {
    const ref = this.modalService.open(IterationCreateComponent);
    ref.componentInstance.project = this.project;
  }

}
