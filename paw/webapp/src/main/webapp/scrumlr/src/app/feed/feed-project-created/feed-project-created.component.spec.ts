import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedProjectCreatedComponent } from './feed-project-created.component';

describe('FeedProjectCreatedComponent', () => {
  let component: FeedProjectCreatedComponent;
  let fixture: ComponentFixture<FeedProjectCreatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedProjectCreatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedProjectCreatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
