import { render, screen } from '@testing-library/react';
import App from './App';

test('renders component app', () => {
  render(<App />);
  expect(screen.getByText(/Books/i)).toBeInTheDocument();
  expect(screen.getByText(/Faça seu Login/i)).toBeInTheDocument();
});
