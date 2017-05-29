import { Component, OnInit } from '@angular/core';

import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ApiService, LoginComponent } from './api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [HeaderComponent, FooterComponent, ApiService, LoginComponent]
})
export class AppComponent implements OnInit {

  ngOnInit(): void {
  }
  title = 'app works!';

  constructor(private apiService: ApiService) { }

}
