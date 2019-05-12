import {IStock} from "./IStock";
import {IEarning} from "./IEarning";

export interface IHolding {
  id: number;
  stock: IStock,
  amount: number,
  totalPrice: number,
  earnings: IEarning[]
}
