import { toCurrencyFormat } from 'commons/utils/currency';

const serverToGrid = data => {
  data.books = data?.books?.map(item => ({
    ...item,
    costValue: toCurrencyFormat(item?.costValue),
    saleValue: toCurrencyFormat(item?.saleValue),
  }));

  return data;
};

export default serverToGrid;
