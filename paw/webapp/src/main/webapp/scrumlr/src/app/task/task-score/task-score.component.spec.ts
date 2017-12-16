import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskScoreComponent } from './task-score.component';

describe('TaskScoreComponent', () => {
  let component: TaskScoreComponent;
  let fixture: ComponentFixture<TaskScoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskScoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskScoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
