import LockIcon from '@mui/icons-material/Lock';
import PersonIcon from '@mui/icons-material/Person';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuth from 'commons/hooks/useAuth';
import Button from 'components/Button';
import TextField from 'components/TextField';
import Typography from 'components/Typography';
import InputAdornment from 'components/Input/InputAdornment';
import { LoginContent, SloganTitle, PaperForm } from './styles';

const LoginPage = () => {
  const { signIn } = useAuth();
  const navigate = useNavigate();

  const callbackRedirect = () => navigate('/clients');

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = event => {
    event.preventDefault();
    signIn({username, password}, callbackRedirect);
  };

  return (
    <LoginContent>
      <div>
        <SloganTitle variant="h3" component="h1">
          Books
        </SloganTitle>
        <Typography variant="h6" component="h4">
          Faça seu Login
        </Typography>
      </div>

      <PaperForm elevation={2}>
        <form noValidate onSubmit={handleSubmit}>
          <TextField
            variant="outlined"
            margin="normal"
            fullWidth
            id="usuario"
            placeholder="Seu login"
            name="usuario"
            autoComplete="usuario"
            autoFocus
            value={username}
            onChange={e => setUsername(e.target.value)}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <PersonIcon />
                </InputAdornment>
              ),
            }}
          />

          <TextField
            variant="outlined"
            margin="normal"
            fullWidth
            name="password"
            placeholder="Sua senha"
            type="password"
            id="password"
            autoComplete="current-password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <LockIcon />
                </InputAdornment>
              ),
            }}
          />

          <Button
            type="submit"
            size="large"
            fullWidth
            variant="contained"
            color="primary"
          >
            Entrar
          </Button>
        </form>
      </PaperForm>
    </LoginContent>
  );
};

export default LoginPage;
