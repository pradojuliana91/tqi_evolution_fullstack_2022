import responseData from 'mocks/data';
import { rest } from 'msw';
import { mockPagination } from 'mocks/utils';

export default [
  rest.get(`/api/authors`, (req, res, ctx) =>
    res(
      ctx.status(200),
      // ctx.json(responseData.authors.page),
      ctx.json(mockPagination(req, responseData.authors.listAll, 'authors')),
    ),
  ),
];
