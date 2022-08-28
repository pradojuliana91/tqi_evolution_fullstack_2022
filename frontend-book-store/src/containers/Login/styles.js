import styled from 'styled-components';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import Paper from 'components/Paper';
import Typography from 'components/Typography';

const LoginCard = styled(Card)`
  width: 320px;
  position: absolute;
  top: 20%;
  left: 0;
  margin: 0 auto;
  right: 0;
  background-color: transparent !important;
  box-shadow: 2px 2px 10px 5px #888888 !important;

  ${props =>
    props.error
      ? `animation: shake 0.82s cubic-bezier(.36,.07,.19,.97) both;
    transform: translate3d(0, 0, 0);
    backface-visibility: hidden;
    perspective: 1000px;
    
    @keyframes shake {
      10%, 90% {
        transform: translate3d(-1px, 0, 0);
      }
      
      20%, 80% {
        transform: translate3d(2px, 0, 0);
      }
    
      30%, 50%, 70% {
        transform: translate3d(-4px, 0, 0);
      }
    
      40%, 60% {
        transform: translate3d(4px, 0, 0);
      }
    }`
      : ''}
`;

const FormContent = styled(CardContent)`
  display: flex;
  flex-direction: column;
  background-color: rgba(255, 255, 255, 0.8);
`;

const LoginHeader = styled(CardHeader)`
  background-color: rgba(0, 208, 208, 0.1);
  color: white;
  height: 100px;
`;

const SubmitButton = styled.button`
  background-color: '#F59101';
  color: white;
  border: none;
  border-radius: 3px;
  padding: 12px 8px;
  font-size: 18px;
  font-weight: 600;
  margin-top: 10px;

  :hover {
    background-color: '#F59202';
  }
`;

const SpanContent = styled.span`
  position: absolute;
  right: 20px;
  top: 90px;
  font-weight: bold;
  font-size: 16px;
`;

const LoginContent = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  min-height: 100vh;
  gap: ${({ theme }) => theme.spacing(5)};
  padding: ${({ theme }) => theme.spacing(2)}px;
`;

const SloganTitle = styled(Typography)`
  margin-bottom: ${({ theme }) => theme.spacing(2)}px;
  color: ${({ theme }) => theme.palette.primary.main};
`;

const PaperForm = styled(Paper)`
  padding: ${({ theme }) => theme.spacing(4)};
  max-width: 500px;
`;

export {
  PaperForm,
  LoginContent,
  LoginCard,
  SubmitButton,
  FormContent,
  LoginHeader,
  SpanContent,
  SloganTitle,
};
