import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-project-dossier',
  templateUrl: './project-dossier.component.html',
  styleUrls: ['./project-dossier.component.scss']
})
export class ProjectDossierComponent implements OnInit {
  @Input() project: any;
  constructor() { }

  ngOnInit() {
  }

}
