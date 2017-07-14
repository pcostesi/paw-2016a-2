import { LOCALE_ID, Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'scrumlr-locale-selector',
  templateUrl: './locale-selector.component.html',
  styleUrls: ['./locale-selector.component.scss']
})
export class LocaleSelectorComponent implements OnInit {
  public lang: string;
  constructor(@Inject(LOCALE_ID) private locale: string) {
    this.lang = this.l18nizeLang(locale);
  }

  public l18nizeLang(lang: string) {
    switch (lang.toLocaleLowerCase()) {
      case 'es':
      return 'Español';
      default:
      return 'English';
    }
  }

  public selectLocale(lang: string) {
    document.cookie = `scrumlr-locale=${lang}`;
    window.location.reload(true);
  }
  public unselectLocale() {
    document.cookie = `scrumlr-locale=; expires=Thu, 01 Jan 1970 00:00:00 GMT`;
    window.location.reload(true);
  }
  ngOnInit() {
  }

}
