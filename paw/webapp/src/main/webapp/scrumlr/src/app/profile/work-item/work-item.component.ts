import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'scrumlr-work-item',
  templateUrl: './work-item.component.html',
  styleUrls: ['./work-item.component.scss']
})
export class WorkItemComponent implements OnInit {
  @Input() work: any;

  constructor() { }

  ngOnInit() {
  }

}
