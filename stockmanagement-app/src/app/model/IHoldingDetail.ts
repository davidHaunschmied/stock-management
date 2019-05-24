import {IHolding} from "./IHolding";

export interface IHoldingDetail extends IHolding {
  absoluteChange: number;
  relativeChange: number;
  currentTotalPrice: number;
}
