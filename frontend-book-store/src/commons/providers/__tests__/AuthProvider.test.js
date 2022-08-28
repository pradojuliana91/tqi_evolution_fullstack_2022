import { useContext } from 'react';
import { MemoryRouter, Routes, Route } from 'react-router-dom';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import AuthProvider from 'commons/providers/AuthProvider';
import { AuthContext } from 'commons/providers/AuthContext';

const MockComponent = () => {
  const { isAuthenticated, signIn, signOut } = useContext(AuthContext);
  return (
    <>
      <p>{isAuthenticated ? 'Logado' : 'Logar'}</p>
      <button onClick={() => signIn('user', '123')}>Login</button>
      <button onClick={() => signOut()}>Logout</button>
    </>
  );
};

const setup = () => {
  const testRenderer = render(
    <MemoryRouter initialEntries={['/books']} initialIndex={0}>
      <Routes>
        <Route
          path="/books"
          exact={true}
          element={
            <AuthProvider>
              <MockComponent />
            </AuthProvider>
          }
        />
      </Routes>
    </MemoryRouter>,
  );

  return {
    ...testRenderer,
    loginButton: testRenderer.getByRole('button', { name: /Login/i }),
    logoutButton: testRenderer.getByRole('button', { name: /Logout/i }),
  };
};

describe('AuthProvider', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  test('should render with default props', () => {
    const { container } = setup();
    expect(container).toBeInTheDocument();
  });

  test('should render login button', () => {
    const { loginButton } = setup();
    expect(loginButton).toBeInTheDocument();

    //   await userEvent.click(loginButton);
    //   expect(getByText(/Logado/i)).toBeInTheDocument();
  });

  test('should render logout button', async () => {
    const { findByText, logoutButton } = setup();
    expect(logoutButton).toBeInTheDocument();
    await userEvent.click(logoutButton);

    expect(await findByText(/Logar/i)).toBeInTheDocument();
  });
});
