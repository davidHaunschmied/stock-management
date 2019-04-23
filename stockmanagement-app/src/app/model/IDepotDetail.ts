import {IStock} from "./IStock";

export interface IDepotDetail {
  id: number;
  name: string;
  createdAt: string;
  updatedAt: string;
  stocks: IStock [];

}
