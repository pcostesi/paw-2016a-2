import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedIterationCreatedComponent } from './feed-iteration-created.component';

describe('FeedIterationCreatedComponent', () => {
  let component: FeedIterationCreatedComponent;
  let fixture: ComponentFixture<FeedIterationCreatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedIterationCreatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedIterationCreatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
