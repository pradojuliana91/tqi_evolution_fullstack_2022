import axiosMock from 'mocks/axios';
import BooksApi from 'services/books';

describe('BooksApi Api', () => {
  beforeEach(() => {
    axiosMock.get.mockResolvedValue({
      data: {
        books: [],
      },
    });
  });

  test('should call get books', () => {
    BooksApi.page();
    expect(axiosMock.get).toHaveBeenCalledWith('/books?page=0&size=5');

    BooksApi.page(2, 5);
    expect(axiosMock.get).toHaveBeenCalledWith('/books?page=2&size=5');
  });
});
