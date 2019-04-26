import {IStockExchange} from "./IStockExchange";

export interface IStock {
  id: number;
  symbol: string;
  name: string;
  price: number;
  currency: string;
  createdAt: string;
  updatedAt: string;
  stockExchange: IStockExchange;
}
