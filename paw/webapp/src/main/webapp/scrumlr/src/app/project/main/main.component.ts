import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'scrumlr-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  public projects = [1, 2, 3];
  constructor() { }

  ngOnInit() {
  }

}
