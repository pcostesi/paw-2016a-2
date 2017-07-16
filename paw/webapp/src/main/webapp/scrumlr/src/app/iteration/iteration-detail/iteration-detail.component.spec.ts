import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IterationDetailComponent } from './iteration-detail.component';

describe('IterationDetailComponent', () => {
  let component: IterationDetailComponent;
  let fixture: ComponentFixture<IterationDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IterationDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IterationDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
