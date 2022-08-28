import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import useAuth from 'commons/hooks/useAuth';
import Typography from 'components/Typography';
import Toolbar from 'components/Toolbar';
import IconButton from 'components/IconButton';
import { LogoutIcon } from 'components/Icons';
import { Container, AppBar, Main } from './styles';

const Layout = ({ children }) => {
  const { signOut } = useAuth();
  const navigate = useNavigate();

  const callbackLogout = () => navigate('/login');

  return (
    <Container>
      <AppBar>
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Bootcamp
          </Typography>
          <IconButton
            onClick={() => signOut(callbackLogout)}
            sx={{ my: 1, mx: 1.5 }}
            color="inherit"
          >
            <LogoutIcon />
          </IconButton>
        </Toolbar>
      </AppBar>

      <Main>{children}</Main>
    </Container>
  );
};

Layout.propTypes = {
  children: PropTypes.any.isRequired,
};

export default Layout;
