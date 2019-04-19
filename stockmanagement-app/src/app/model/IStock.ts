import {IStockExchange} from "./IStockExchange";

export interface IStock {
  id: number;
  symbol: string;
  name: string;
  currency: string;
  createdAt: string;
  updatedAt: string;
  stockExchange: IStockExchange;
}
