import { Component, OnInit, Input, OnChanges } from '@angular/core';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { BacklogService } from '../backlog.service';
import { BacklogActivity } from '../backlog-activity.enum';
import { BacklogItem } from '../backlog-item';
import { CreateComponent } from '../create/create.component';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-backlog',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit, OnChanges {
  public backlog?: BacklogItem[] = undefined;
  @Input() code: string;

  constructor(private backlogService: BacklogService,
    private modalService: NgbModal) { }

  ngOnInit() { }

  ngOnChanges(changes: any) {
    this.updateBacklog(changes.code.currentValue);
  }

  updateBacklog(code: string) {
    if (code) {
      this.backlogService.getBacklogItems(code)
        .subscribe(backlog => this.backlog = backlog);

      this.backlogService.events()
        .filter(event => event !== BacklogActivity.FETCHED)
        .flatMap(event => {
          return this.backlogService.getBacklogItems(code);
        })
        .subscribe(backlog => this.backlog = backlog);
    }
  }

  createItem() {
    const ref = this.modalService.open(CreateComponent);
    ref.componentInstance.project = this.code;
  }
}
