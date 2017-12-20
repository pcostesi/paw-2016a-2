import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedStoryCreatedComponent } from './feed-story-created.component';

describe('FeedStoryCreatedComponent', () => {
  let component: FeedStoryCreatedComponent;
  let fixture: ComponentFixture<FeedStoryCreatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedStoryCreatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedStoryCreatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
