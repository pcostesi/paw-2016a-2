import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { ApiService, LoginEvent } from '.';

export interface UserProfile {
  username: string;
  mail: string;
}

export type MaybeUser = UserProfile | null;

@Injectable()
export class AccountService {

  private user: MaybeUser;
  private loginSubject = new Subject<MaybeUser>();

  constructor(private api: ApiService) {
    this.api.loginStatusTopic().subscribe(status => {
      switch (status) {
        case LoginEvent.SET_CREDENTIALS:
          this.fetchLoggedAccount().subscribe(user => {
            this.user = user;
            this.loginSubject.next(user);
          }, error => {
            this.loginSubject.next(null);
          });
          break;

        default:
          this.user = null;
          this.loginSubject.next(null);
          break;
      }
    });
  }

  get stream() {
    return this.loginSubject.asObservable();
  }

  public getLoggedAccount(): MaybeUser {
    return this.user;
  }

  private fetchLoggedAccount(): Observable<MaybeUser> {
    if (!this.api.hasCredentialsSet()) {
      return Observable.throw(null);
    }
    return this.api.get('/user/me').map(result => {
      if (result.status === 200) {
        return result.json() as UserProfile;
      }
      throw result;
    });
  }
}
