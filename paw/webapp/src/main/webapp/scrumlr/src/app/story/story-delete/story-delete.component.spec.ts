import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StoryDeleteComponent } from './story-delete.component';

describe('StoryDeleteComponent', () => {
  let component: StoryDeleteComponent;
  let fixture: ComponentFixture<StoryDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StoryDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StoryDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
