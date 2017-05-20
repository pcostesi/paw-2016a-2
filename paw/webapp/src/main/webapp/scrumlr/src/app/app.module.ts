import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { APP_BASE_HREF } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';
import { ApiModule } from './api';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { SampleComponent } from './sample/sample.component';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SampleComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ApiModule,
    AppRoutingModule
  ],
  providers: [{provide: APP_BASE_HREF, useValue: environment.baseUri}],
  bootstrap: [AppComponent]
})
export class AppModule { }
