import {IStock} from "./IStock";

export interface IDepot {
  id: number;
  name: string;
  createdAt: string;
  updatedAt: string;
  stocks: IStock [];

}
