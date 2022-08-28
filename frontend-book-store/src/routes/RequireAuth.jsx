import PropTypes from 'prop-types';
import { Navigate } from 'react-router-dom';
import useAuth from 'commons/hooks/useAuth';

const RequireAuth = ({ children, redirectTo }) => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? children : <Navigate to={redirectTo} replace />;
};

RequireAuth.propTypes = {
  children: PropTypes.any.isRequired,
  redirectTo: PropTypes.string.isRequired,
};

export default RequireAuth;
