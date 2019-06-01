import {IStock} from "./IStock";
import {IEarning} from "./IEarning";
import {IDepot} from "./IDepot";

export interface IHolding {
  id: number;
  stock: IStock,
  amount: number,
  totalPrice: number,
  earnings: IEarning[],
  depot: IDepot
}
