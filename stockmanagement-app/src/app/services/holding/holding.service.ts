import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {IHolding} from "../../model/IHolding";
import {AppSettings} from "../../app-settings";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class HoldingService {

  readonly endpoint = AppSettings.API_ENDPOINT + '/holdings';

  constructor(private http: HttpClient) {
  }

  getAllHoldings(depotId: number): Observable<IHolding[]> {
    return this.http.get<IHolding[]>(this.endpoint + '/' + depotId + '/', AppSettings.HTTP_OPTIONS);
  }

  getAllHoldingsByStock(stockId: number): Observable<IHolding[]>{
    return this.http.get<IHolding[]>(this.endpoint + '/byStock/' + stockId + '/', AppSettings.HTTP_OPTIONS);
  }

}
