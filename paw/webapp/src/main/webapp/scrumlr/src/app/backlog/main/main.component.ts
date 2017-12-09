import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { BacklogService } from '../backlog.service';
import { BacklogActivity } from '../backlog-activity.enum';
import { BacklogItem } from '../backlog-item';
import { CreateComponent } from '../create/create.component';

@Component({
  selector: 'app-backlog',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  public backlog: BacklogItem[] = [];
  @Input() project: any;

  constructor(private backlogService: BacklogService,
    private modalService: NgbModal) { }

  ngOnInit() {
    this.backlogService.events().subscribe(event => {
      if (event !== BacklogActivity.FETCHED) {
        this.fetchBacklogForProject(this.project.code);
      }
    });
    this.backlogService.getBacklogItems(this.project.code)
      .subscribe(items => this.backlog = items);
  }

  private fetchBacklogForProject(project: string) {
    return this.backlogService.getBacklogItems(project)
      .subscribe((backlog: BacklogItem[]) => {
        this.backlog = backlog;
      });
  }

  createItem() {
    const ref = this.modalService.open(CreateComponent);
    ref.componentInstance.project = this.project.code;
  }
}
