import {Injectable} from '@angular/core';
import {AppSettings} from "../../app-settings";
import {HttpClient} from "@angular/common/http";
import {IStock} from "../../model/IStock";
import {Observable} from "rxjs";
import {IDepot} from "../../model/IDepot";
import {IHolding} from "../../model/IHolding";

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  readonly endpoint = AppSettings.API_ENDPOINT + '/transactions';

  constructor(private http: HttpClient) {
  }

  sellStock(stock: IStock, depot: IDepot, amount: number, price: number): Observable<IHolding> {
    return this.http.post<IHolding>(this.endpoint + '/sell',
      {
        'stockId': stock.id,
        'depotId': depot.id,
        'amount': amount,
        'price': price
      }, AppSettings.HTTP_OPTIONS);
  }

  purchaseStock(stock: IStock, depot: IDepot, amount: number, price: number): Observable<IHolding> {
    return this.http.post<IHolding>(this.endpoint + '/purchase',
      {
        'stockId': stock.id,
        'depotId': depot.id,
        'amount': amount,
        'price': price
      }, AppSettings.HTTP_OPTIONS);
  }
}
