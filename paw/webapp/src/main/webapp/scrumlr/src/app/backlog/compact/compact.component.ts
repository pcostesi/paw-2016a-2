import { Component, OnInit, Input } from '@angular/core';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { BacklogItem } from '../backlog-item';
import { EditComponent } from '../edit/edit.component';
import { DeleteComponent } from '../delete/delete.component';

@Component({
  selector: 'scrumlr-compact',
  templateUrl: './compact.component.html',
  styleUrls: ['./compact.component.scss'],
})
export class CompactComponent implements OnInit {
  @Input() item: BacklogItem;
  @Input() project: string;

  constructor(private modalService: NgbModal) { }

  ngOnInit() {
  }


  editItem() {
    const ref = this.modalService.open(EditComponent);
    ref.componentInstance.item = this.item;
    ref.componentInstance.project = this.project;
  }

  deleteItem() {
    const ref = this.modalService.open(DeleteComponent);
    ref.componentInstance.item = this.item;
    ref.componentInstance.project = this.project;
  }
}
