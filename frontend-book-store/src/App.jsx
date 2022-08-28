import Router from 'routes/Router';
import MainProvider from 'commons/providers/MainProvider';
import AuthProvider from 'commons/providers/AuthProvider';

const App = () => (
  <MainProvider>
    <AuthProvider>
      <Router />
    </AuthProvider>
  </MainProvider>
);

export default App;
