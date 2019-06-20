import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {IHolding} from "../../model/IHolding";
import {AppSettings} from "../../app-settings";
import {HttpClient} from "@angular/common/http";
import {IDepot} from "../../model/IDepot";

@Injectable({
  providedIn: 'root'
})
export class HoldingService {

  readonly current_depot_storage = 'CURRENT_DEPOT';
  readonly endpoint = AppSettings.API_ENDPOINT + '/holdings';

  public currentDepot;

  constructor(private http: HttpClient) {
    this.currentDepot = new BehaviorSubject<IDepot>(JSON.parse(localStorage.getItem(this.current_depot_storage)));
  }

  getAllHoldings(depotId: number): Observable<IHolding[]> {
    return this.http.get<IHolding[]>(this.endpoint + '/' + depotId + '/', AppSettings.HTTP_OPTIONS);
  }

  getAllHoldingsByStock(stockId: number): Observable<IHolding[]>{
    return this.http.get<IHolding[]>(this.endpoint + '/byStock/' + stockId + '/', AppSettings.HTTP_OPTIONS);
  }

}
