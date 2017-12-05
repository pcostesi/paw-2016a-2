import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { ApiService } from './api.service';
import { AccountService } from './account.service';

@Injectable()
export class LoginGuard implements CanActivate {
  constructor(private account: AccountService, private api: ApiService) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    const userHasLoggedIn = this.account.getLoggedAccount() != null;
    if (!userHasLoggedIn) {
      this.api.requestCredentials();
      return this.account.stream.map(user => !!user);
    }
    return userHasLoggedIn;
  }
}
