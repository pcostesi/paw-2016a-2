import { TestBed, inject } from '@angular/core/testing';

import { IterationService } from './iteration.service';

describe('IterationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IterationService]
    });
  });

  it('should ...', inject([IterationService], (service: IterationService) => {
    expect(service).toBeTruthy();
  }));
});
