import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IterationDeleteComponent } from './iteration-delete.component';

describe('IterationDeleteComponent', () => {
  let component: IterationDeleteComponent;
  let fixture: ComponentFixture<IterationDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IterationDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IterationDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
