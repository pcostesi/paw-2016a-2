import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IterationListComponent } from './iteration-list.component';

describe('IterationListComponent', () => {
  let component: IterationListComponent;
  let fixture: ComponentFixture<IterationListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IterationListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IterationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
