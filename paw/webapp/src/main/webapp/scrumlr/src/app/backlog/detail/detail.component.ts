import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { BacklogItem } from '../backlog-item';
import { EditComponent } from '../edit/edit.component';
import { DeleteComponent } from '../delete/delete.component';
import { BacklogService } from '../backlog.service';
import { BacklogActivity } from '../backlog-activity.enum';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  item: BacklogItem;
  project: string;
  backlogId: string;

  constructor(private modalService: NgbModal,
    private backlogService: BacklogService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.project = params['proj'];
      this.backlogId = params['id'];
      this.fetchBacklogItem(this.project, this.backlogId);
    });
    this.backlogService.events().subscribe(event => {
      if (event !== BacklogActivity.FETCHED) {
        this.fetchBacklogItem(this.project, this.backlogId);
      }
    });
  }

  private fetchBacklogItem(project: string, id: string) {
    return this.backlogService.getBacklogItem(project, id).subscribe(response => {
      if (response) {
        this.item = response;
      }
    });
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
