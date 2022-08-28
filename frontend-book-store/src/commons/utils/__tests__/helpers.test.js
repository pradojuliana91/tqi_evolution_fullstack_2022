import { emptyToNull, isEmpty } from 'commons/utils/helpers';

describe('#emptyToNull', () => {
  test('should return null when value is null', () => {
    expect(emptyToNull('')).toBeNull();
  });

  test('should return null when value is undefined', () => {
    expect(emptyToNull(undefined)).toBeNull();
  });
});

describe('#isEmpty', () => {
  test('should return empty when value is null', () => {
    expect(isEmpty(null)).toBeTruthy();
  });

  test('should return empty when value is undefined', () => {
    expect(isEmpty()).toBeTruthy();
  });

  test('should return empty when value is array with length equals zero', () => {
    expect(isEmpty([])).toBeTruthy();
  });

  test('should return empty when value is object keys.length equals zero', () => {
    expect(isEmpty({})).toBeTruthy();
  });
});
