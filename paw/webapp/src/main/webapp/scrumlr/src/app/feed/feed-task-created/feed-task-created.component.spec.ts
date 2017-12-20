import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedTaskCreatedComponent } from './feed-task-created.component';

describe('FeedTaskCreatedComponent', () => {
  let component: FeedTaskCreatedComponent;
  let fixture: ComponentFixture<FeedTaskCreatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedTaskCreatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedTaskCreatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
