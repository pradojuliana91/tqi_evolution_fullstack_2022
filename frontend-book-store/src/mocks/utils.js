const mockLazyList = (page, rowsPerPage, array, arrayName = 'items') => {
  const pageNumber = +page;
  const start = pageNumber * +rowsPerPage;
  const end = start + +rowsPerPage;
  const totalItemCount = array?.length || 0;
  const items = array?.slice(start, end);

  return {
    count: totalItemCount,
    page: pageNumber,
    size: +rowsPerPage,
    [arrayName]: items,
  };
};

const mockPagination = (req, listAll, arrayName) => {
  const page = req.url?.searchParams?.get('page') || 0;
  const perPage = req.url?.searchParams?.get('size') || 5;

  return mockLazyList(page, perPage, listAll, arrayName);
};

export { mockLazyList, mockPagination };
