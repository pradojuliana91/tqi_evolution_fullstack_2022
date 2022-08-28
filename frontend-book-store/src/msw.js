import { worker } from './mocks/browser';

console.log('#### worker.start() ####');

worker.start({
  warnOnUncaptured: false,
  // quiet: true,
  onUnhandledRequest: req => {
    if (req.url.href.includes('/api')) {
      console.error(
        'Encontrou uma solicitação %s não tratada para %s',
        req.method,
        req.url.href,
      );
    }
  },
  // serviceWorker: { url: `${process.env.PUBLIC_URL}/mockServiceWorker.js` },
});
