import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../api';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  private username: string;
  private mail: string;

  constructor(private api: ApiService) { }

  ngOnInit() {
    this.api.get('/user/me').subscribe(success => {
      this.username = success['username'];
      this.mail = success['mail'];
    }, error => {
      this.username = 'oopsie';
      this.mail = 'my@bad.com';
    });
  }

}
