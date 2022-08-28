import { createContext } from 'react';

const AuthContext = createContext();

AuthContext.displayName = 'AuthContext';

const { Provider, Consumer } = AuthContext;

export { Provider, Consumer, AuthContext };
