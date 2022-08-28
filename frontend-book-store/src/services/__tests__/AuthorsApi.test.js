import axiosMock from 'mocks/axios';
import AuthorApi from 'services/authors';

describe('Authors Api', () => {
  beforeEach(() => {
    axiosMock.get.mockResolvedValue({
      data: {
        authors: [],
      },
    });
  });

  test('should call get authors', () => {
    AuthorApi.page();
    expect(axiosMock.get).toHaveBeenCalledWith('/authors?page=0&size=5');

    AuthorApi.page(2, 5);
    expect(axiosMock.get).toHaveBeenCalledWith('/authors?page=2&size=5');
  });
});
