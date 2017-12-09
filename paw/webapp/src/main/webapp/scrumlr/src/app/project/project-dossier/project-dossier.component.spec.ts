import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectDossierComponent } from './project-dossier.component';

describe('ProjectDossierComponent', () => {
  let component: ProjectDossierComponent;
  let fixture: ComponentFixture<ProjectDossierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectDossierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectDossierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
