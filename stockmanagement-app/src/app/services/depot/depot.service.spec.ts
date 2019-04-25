import {TestBed} from '@angular/core/testing';

import {DepotService} from './depot.service';
import {TestcoreModule} from "../core/testcore.module";

describe('DepotService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [TestcoreModule]
  }));

  it('should be created', () => {
    const service: DepotService = TestBed.get(DepotService);
    expect(service).toBeTruthy();
  });
});
