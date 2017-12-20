import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedBacklogItemCreatedComponent } from './feed-backlog-item-created.component';

describe('FeedBacklogItemCreatedComponent', () => {
  let component: FeedBacklogItemCreatedComponent;
  let fixture: ComponentFixture<FeedBacklogItemCreatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedBacklogItemCreatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedBacklogItemCreatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
