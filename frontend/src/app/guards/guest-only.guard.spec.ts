import { TestBed } from '@angular/core/testing';

import { GuestOnlyGuard } from './guest-only.guard';

describe('PublicOnlyGuard', () => {
  let guard: GuestOnlyGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(GuestOnlyGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
