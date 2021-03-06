import {Injectable} from '@angular/core';
import {AppSettings} from "../../app-settings";
import {HttpClient} from "@angular/common/http";
import {IStock} from "../../model/IStock";
import {Observable} from "rxjs";
import {IDepot} from "../../model/IDepot";
import {IHolding} from "../../model/IHolding";
import {ITransaction} from "../../model/ITransaction";

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  readonly endpoint = AppSettings.API_ENDPOINT + '/transactions';

  constructor(private http: HttpClient) {
  }

  getAllTransactionsByDepot(depotId: number): Observable<ITransaction[]> {
    return this.http.get<ITransaction[]>(this.endpoint + '/' + depotId, AppSettings.HTTP_OPTIONS);
  }

  sellStock(stock: IStock, depot: IDepot, amount: number): Observable<IHolding> {
    return this.newTransaction(stock, depot, amount, '/sell');
  }

  purchaseStock(stock: IStock, depot: IDepot, amount: number): Observable<IHolding> {
    return this.newTransaction(stock, depot, amount, '/purchase');
  }

  private newTransaction(stock: IStock, depot: IDepot, amount: number, subPath: string): Observable<IHolding> {
    return this.http.post<IHolding>(this.endpoint + subPath,
      {
        'stockId': stock.id,
        'depotId': depot.id,
        'amount': amount
      }, AppSettings.HTTP_OPTIONS);
  }
}
