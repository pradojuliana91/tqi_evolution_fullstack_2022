import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { addStorageListener, logout, login, getJwt, clearJwt } from 'commons/utils/auth';
import { Provider } from './AuthContext';

const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(true);

  const signIn = async ({ username, password }, callback) => {
    try {
      await login({ username, password }, callback);
      if(getJwt()) {
        setUser(username);
        setIsAuthenticated(true);
       } else {
        setUser(null); 
        setIsAuthenticated(false);
        clearJwt();
       }
    }catch (e) {
      setUser(null);
      setIsAuthenticated(false);
      console.error('ERROR: Não possível efetuar o login!');
    }
  };

  const signOut = async callback => {
    try {
      await logout(callback);
      setIsAuthenticated(false);
    } catch (e) {
      setUser(null);
      console.error('ERROR: Não possível efetuar o logout!');
    }
  };

  useEffect(() => {
    addStorageListener();
  }, []);

  return (
    <Provider value={{ user, signIn, signOut, isAuthenticated }}>
      {children}
    </Provider>
  );
};

AuthProvider.propTypes = {
  children: PropTypes.any,
};

export default AuthProvider;
