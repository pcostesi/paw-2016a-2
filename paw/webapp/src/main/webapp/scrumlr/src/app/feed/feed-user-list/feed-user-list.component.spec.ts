import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedUserListComponent } from './feed-user-list.component';

describe('FeedUserListComponent', () => {
  let component: FeedUserListComponent;
  let fixture: ComponentFixture<FeedUserListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedUserListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedUserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
