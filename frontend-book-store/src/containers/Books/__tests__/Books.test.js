import { render } from 'test-utils';
import { waitFor } from '@testing-library/react';
import Books from 'containers/Books';

const setup = () => {
  const testRenderer = render(<Books />);

  return {
    ...testRenderer,
    newBookButton: testRenderer.getByTitle(/Novo Livro/i),
    authorsButton: testRenderer.getByRole('button', { name: /Autores/i }),
  };
};

describe('Books', () => {
  afterEach(() => jest.clearAllMocks());

  test('should render component', () => {
    const { container } = setup();

    expect(container).toBeInTheDocument();
  });

  test('should render title', () => {
    const { getByText } = setup();

    expect(getByText(/Livros/i)).toBeInTheDocument();
  });

  test('should render data grid with lines by request api', async () => {
    const { findAllByRole } = setup();

    await waitFor(async () => {
      expect(await findAllByRole('columnheader')).toHaveLength(4);
      expect(await findAllByRole('row')).toHaveLength(6);
    });
  });
});
