import {
  isStatusFatal,
  isStatusUnavailable,
  isStatusTimeout,
  isStatusBadRequest,
  STATUS,
} from 'services/errors';

const STATUS_OK = 200;

describe('#isStatusFatal', () => {
  test('should return true if error is Fatal', () => {
    expect(isStatusFatal(STATUS.FATAL)).toBeTruthy();
  });

  test('should return false when diff fatal', () => {
    expect(isStatusFatal(STATUS_OK)).toBeFalsy();
  });
});

describe('#isStatusUnavailable', () => {
  test('should return true if error unavailable', () => {
    expect(isStatusUnavailable(STATUS.UNAVAILABLE)).toBeTruthy();
  });

  test('should return false when diff unavailable', () => {
    expect(isStatusUnavailable(STATUS_OK)).toBeFalsy();
  });
});

describe('#isStatusTimeout', () => {
  test('should return true if error Timeout', () => {
    expect(isStatusTimeout(STATUS.TIMEOUT)).toBeTruthy();
  });

  test('should return false when diff Timeout', () => {
    expect(isStatusTimeout(STATUS_OK)).toBeFalsy();
  });
});

describe('#isStatusBadRequest', () => {
  test('should return true if error Bad Request', () => {
    expect(isStatusBadRequest(STATUS.BAD_REQUEST)).toBeTruthy();
  });

  test('should return false when diff Bad Request', () => {
    expect(isStatusBadRequest(STATUS_OK)).toBeFalsy();
  });
});
