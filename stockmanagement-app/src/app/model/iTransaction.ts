import {IHolding} from "./IHolding";

export interface ITransaction {
  id: number;
  amount: number;
  price: number;
  data: Date;
  holding: IHolding;
  transactionType: TransactionType;
}

export enum TransactionType {
  PURCHASE,
  SALE
}
