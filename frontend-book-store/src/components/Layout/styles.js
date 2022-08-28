import styled, { css } from 'styled-components';
import MuiAppBar from 'components/AppBar';

const Container = styled.div`
  display: flex;
`;

const AppBar = styled(MuiAppBar)`
  z-index: ${({ theme }) => theme.zIndex.drawer + 1};
`;

const Main = styled.main`
  flex-grow: 1;
  margin: ${({ theme }) => theme.spacing(11)} ${({ theme }) => theme.spacing(3)};

  ${({ theme }) => css`
    ${theme.breakpoints.down('xs')} {
      margin: ${({ theme }) => theme.spacing(11)}
        ${({ theme }) => theme.spacing(1)};
    }
  `}
`;

export { Container, AppBar, Main };
