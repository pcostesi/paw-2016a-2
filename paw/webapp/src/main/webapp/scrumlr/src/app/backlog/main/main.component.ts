import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { BacklogService } from '../backlog.service';
import { BacklogActivity } from '../backlog-activity.enum';
import { BacklogItem } from '../backlog-item';
import { CreateComponent } from '../create/create.component';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  public backlog: BacklogItem[] = [];
  public project: string;

  constructor(private backlogService: BacklogService,
    private route: ActivatedRoute,
    private modalService: NgbModal) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.project = params['proj'];
      this.fetchBacklogForProject(this.project);
    });
    this.backlogService.events().subscribe(event => {
      if (event !== BacklogActivity.FETCHED) {
        this.fetchBacklogForProject(this.project);
      }
    });
  }

  private fetchBacklogForProject(project: string) {
    return this.backlogService.getBacklogItems(project)
      .subscribe((backlog: BacklogItem[]) => {
        this.backlog = backlog;
      });
  }

  createItem() {
    const ref = this.modalService.open(CreateComponent);
    ref.componentInstance.project = this.project;
  }
}
