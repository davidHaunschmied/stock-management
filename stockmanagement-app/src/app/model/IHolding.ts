import {IStock} from "./IStock";

export interface IHolding {
  id: number;
  stock: IStock,
  amount: number,
  totalPrice: number,
  earning: number
}
