/* eslint-disable import/no-anonymous-default-export */
import responseData from '../data';
import { rest } from 'msw';
import { mockPagination } from 'mocks/utils';

export default [
  rest.get(`/api/books`, (req, res, ctx) =>
    res(
      ctx.status(200),
      // ctx.json(responseData.books.page),
      ctx.json(mockPagination(req, responseData.books.listAll, 'books')),
    ),
  ),

  rest.get(`/api/books/:id`, (req, res, ctx) => {
    const { id: uuId } = req.params;
    const book = responseData.books.listAll.find(item => item.code === uuId);

    if (!book) {
      return res(ctx.status(404), ctx.json(responseData.books.get404));
    }

    return res(ctx.status(200), ctx.json(book));
    // return res(ctx.status(200), ctx.json(responseData.books.book));
  }),

  rest.post(`/api/books`, (req, res, ctx) =>
    res(ctx.status(201), ctx.json(responseData.books.post)),
  ),

  rest.put(`/api/books/:id`, (req, res, ctx) => res(ctx.status(204))),

  rest.patch(`/api/books/:id`, (req, res, ctx) => res(ctx.status(204))),

  rest.delete(`/api/books/:id`, (req, res, ctx) => res(ctx.status(204))),
];
