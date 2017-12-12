import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IterationEditComponent } from './iteration-edit.component';

describe('IterationEditComponent', () => {
  let component: IterationEditComponent;
  let fixture: ComponentFixture<IterationEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IterationEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IterationEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
