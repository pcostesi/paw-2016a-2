import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IterationCreateComponent } from './iteration-create.component';

describe('IterationCreateComponent', () => {
  let component: IterationCreateComponent;
  let fixture: ComponentFixture<IterationCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IterationCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IterationCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
