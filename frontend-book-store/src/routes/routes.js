/**
 * Exemplo para utilizacao de rotas com lazy loading.
 */
import { lazy } from 'react';
import Layout from 'components/Layout';
const ClientsPage = lazy(() => import('containers/Clients'));
const ClientsForm = lazy(() => import('containers/Clients/Form'));
const AuthorsPage = lazy(() => import('containers/Authors'));
const AuthorsForm = lazy(() => import('containers/Authors/Form'));
const BooksPage = lazy(() => import('containers/Books'));
const BookForm = lazy(() => import('containers/Books/Form'));



const routes = () => [
  {
    path: '/clients',
    component: ClientsPage,
    layout: Layout,
    exact: true,
  },
  {
    path: '/clients/new',
    component: ClientsForm,
    layout: Layout,
    exact: true,
  },
  {
    path: '/clients/:id',
    component: ClientsForm,
    layout: Layout,
    exact: true,
  },    
  {
    path: '/authors',
    component: AuthorsPage,
    layout: Layout,
    exact: true,
  },
  {
    path: '/authors/new',
    component: AuthorsForm,
    layout: Layout,
    exact: true,
  },
  {
    path: '/authors/:id',
    component: AuthorsForm,
    layout: Layout,
    exact: true,
  },   

  {
    path: '/books',
    component: BooksPage,
    layout: Layout,
    exact: true,
  },
  {
    path: '/books/new',
    component: BookForm,
    layout: Layout,
    exact: true,
  },
  {
    path: '/books/:id',
    component: BookForm,
    layout: Layout,
    exact: true,
  },
];

export default routes;
