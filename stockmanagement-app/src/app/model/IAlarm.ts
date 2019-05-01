import {IStock} from "./IStock";

export interface IAlarm {
  id: number;
  stock: IStock;
  price: number;
  alarmType: string;
}
