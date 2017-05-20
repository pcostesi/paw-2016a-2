import { Component, OnInit } from '@angular/core';

import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ApiService } from './api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [HeaderComponent, FooterComponent, ApiService]
})
export class AppComponent implements OnInit {

  ngOnInit(): void {
    this.getData();
  }
  title = 'app works!';

  constructor(private apiService: ApiService) { }

  getData() {
    this.apiService.setCredentials('test', 'test');
    //this.apiService.get('/sample').subscribe(response => console.log(response));
  }
}
