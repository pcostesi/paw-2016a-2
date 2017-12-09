import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { AccountService, MaybeUser } from '../../api';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  public user: Observable<MaybeUser>;

  constructor(private route: ActivatedRoute, private accounts: AccountService) {
    this.user = route.params.flatMap(({ username }) => {
      return this.accounts.getUser(username);
    });
  }

  ngOnInit() { }

}
