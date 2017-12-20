import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedUserCreatedComponent } from './feed-user-created.component';

describe('FeedUserCreatedComponent', () => {
  let component: FeedUserCreatedComponent;
  let fixture: ComponentFixture<FeedUserCreatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedUserCreatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedUserCreatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
