import { useContext } from 'react';
import { AuthContext } from 'commons/providers/AuthContext';

const useAuth = () => {
  const data = useContext(AuthContext);

  if (!data) {
    throw new Error(
      'O hook useAuth deve ser usado dentro da árvore de componentes do AuthContext',
    );
  }
  return data;
};

export default useAuth;
