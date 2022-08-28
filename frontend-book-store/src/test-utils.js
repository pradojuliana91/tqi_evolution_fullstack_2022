/* eslint-disable import/export */
/* eslint-disable react/prop-types */
import { render, configure, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import MainProvider from 'commons/providers/MainProvider';

configure({ testIdAttribute: 'id' });

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => jest.fn(),
}));

const Wrapper = ({ children }) => <MainProvider>{children}</MainProvider>;

const RouteWrapper = ({ children }) => (
  <Router>
    <Wrapper>{children}</Wrapper>
  </Router>
);

const customRender = (ui, options) =>
  render(ui, { wrapper: Wrapper, ...options });

const renderWithRouter = (ui, { route = '/', ...options } = {}) => {
  window.history.pushState({}, 'Test page', route);

  return render(ui, { wrapper: RouteWrapper, ...options });
};

const asFragment = ui => {
  const { asFragment } = customRender(ui);
  return asFragment(ui);
};

const toMatchSnapshot = (Component, { useRouter = false } = {}) => {
  const wrapper = asFragment(
    useRouter ? <Router>{Component}</Router> : Component,
  );
  expect(wrapper).toMatchSnapshot();
};

export * from '@testing-library/react';
export { default as userEvent } from '@testing-library/user-event';
export {
  customRender as render,
  screen,
  toMatchSnapshot,
  asFragment,
  renderWithRouter,
};
