import { rest } from 'msw';

export default [
  rest.post(`/api/auth/login`, (req, res, ctx) => 
  res(
    ctx.status(200), 
    ctx.json({token: 'oksdt9773324'}))),

  rest.post(`/api/auth/logout`, (req, res, ctx) => res(ctx.status(204))),
];
