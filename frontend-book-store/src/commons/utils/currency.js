/* eslint-disable new-cap */

const filterNotNums = (value = '') => String(value).replace(/\D/g, '');

const toCurrency = (value = 0) =>
  Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(
    value,
  );

const toFloat = (value = '') =>
  typeof value === 'string'
    ? parseFloat(
        value
          .replace(/[R$]/g, '')
          .replace(/[.]/g, '')
          .replace(/[,]/g, '.')
          .replace(/\s/g, ''),
      )
    : value;

const toCurrencyFormat = value => toCurrency(filterNotNums(value) / 100);

const toRound = num => (num ? Math.round(num * 100) / 100 : 0);

export { toRound, toCurrencyFormat, toCurrency, toFloat };
