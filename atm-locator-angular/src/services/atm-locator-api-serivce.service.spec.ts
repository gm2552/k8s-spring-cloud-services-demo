import { TestBed } from '@angular/core/testing';

import { AtmLocatorApiSerivce } from './atm-locator-api-serivce.service';

describe('AtmLocatorApiSerivceService', () => {
  let service: AtmLocatorApiSerivce;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AtmLocatorApiSerivce);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
