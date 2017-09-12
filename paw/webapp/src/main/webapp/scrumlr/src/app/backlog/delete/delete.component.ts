import { Component, OnInit, Input } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { BacklogItem } from '../backlog-item';
import { BacklogService } from '../backlog.service';

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.scss']
})
export class DeleteComponent implements OnInit {
  @Input() item: BacklogItem;
  @Input() project: string;

  constructor(public backlogService: BacklogService,
    public modal: NgbActiveModal) { }

  ngOnInit() {
  }

  deleteItem() {
    this.backlogService.deleteBacklogItem(this.project, this.item)
      .subscribe(ok => {
        if (ok) {
          this.modal.close(ok);
        }
      });
  }

}
