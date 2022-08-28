import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  html,
  body {
    padding: 0;
    margin: 0;
    font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen,
      Ubuntu, Cantarell, Fira Sans, Droid Sans, Helvetica Neue, sans-serif;
    background-color: #fff;
  }

  a {
    color: inherit;
    text-decoration: none;
  }

  * {
    box-sizing: border-box;
  }

  form {
    width: 100%;
  }

  ::-webkit-scrollbar {
    width: 6px!important;
    height: 6px!important;
  }

  ::-webkit-scrollbar-thumb {
    background-color: rgba(0,0,0,.2);
  }

  ::-webkit-scrollbar-track {
    background: rgba(0,0%,100%,.1);
  }
`;

export default GlobalStyle;
