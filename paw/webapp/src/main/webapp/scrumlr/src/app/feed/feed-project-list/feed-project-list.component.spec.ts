import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedProjectListComponent } from './feed-project-list.component';

describe('FeedProjectListComponent', () => {
  let component: FeedProjectListComponent;
  let fixture: ComponentFixture<FeedProjectListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedProjectListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedProjectListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
