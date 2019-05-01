import {IStockExchange} from "./IStockExchange";

export interface IStock {
  id: number;
  symbol: string;
  name: string;
  price: number;
  day_change: number;
  currency: string;
  createdAt: string;
  updatedAt: string;
  stockExchange: IStockExchange;
}
