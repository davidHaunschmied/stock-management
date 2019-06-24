import {IHolding} from "./IHolding";

export interface ITransaction {
  id: number;
  amount: number;
  price: number;
  date: Date;
  holding: IHolding;
  transactionType: TransactionType;
}

export enum TransactionType {
  PURCHASE,
  SALE
}
